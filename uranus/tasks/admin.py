from django.contrib import admin
from tasks.models import *
import csv
from django.http import HttpResponse
from subprocess import call
from os import system
import os
import xlsxwriter
from django.contrib import messages
from dateutil import relativedelta as rdelta
import datetime
from datetime import date
import subprocess
from subprocess import Popen, PIPE
import speech_recognition as sr
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize
import os
import zipfile

# Register your models here.
admin.site.register(Problem)
admin.site.register(Attempt)
admin.site.register(ClassTag)

# Configure administrator view
admin.site.site_header = 'URANUS Administrator'
admin.site.site_title = 'URANUS Administrator'

# Class for the process data
class ExportCsvMixin:

	# Data process function
	def export_as_csv(self, request, queryset):
		# Define variables

		BASE = os.path.abspath(os.path.join(os.path.dirname(__file__), '..', '..'))
		print(BASE)
		REPORT = BASE
		PATH = BASE		
		sistemaop = os.name
		if sistemaop == 'posix':
			PATH = os.path.join(PATH, 'java', 'UranusCB-V2.0', 'launch.sh')
		else:
			PATH = os.path.join(PATH, 'java', 'UranusCB-V2.0', 'launch.cmd')
		# Create persist folder
		# os.mkdir( path, 0755 );
		directory = 'NONE'
		for obj in queryset:
			resoluterID = obj.resoluter_id
			try:
				directory = os.path.join(REPORT, 'java', 'UranusCB-V2.0', 'data', 'capture', resoluterID)
				if not os.path.exists(directory):
					print(f"directory created in {directory}")
					os.mkdir(directory);
			except IOError as e:
				messages.warning(request, 'Problema E001: por favor contactar a soporte.')

			# Generate xlsx
			if directory != 'NONE':
				#-------------------------------------------------
				# Make resoluter info
				workbook = xlsxwriter.Workbook(os.path.join(directory, 'ResolutorInfo.xlsx'))
				worksheet = workbook.add_worksheet("Information")
				worksheet.write('A1', 'RESOLUTOR_ID')
				worksheet.write('A2', resoluterID)
				worksheet.write('B1', 'ALIAS')
				alias = obj.alias
				worksheet.write('B2', alias)
				worksheet.write('C1', 'FECHA_NACIMIENTO')
				bornDate = obj.born_date
				worksheet.write('C2', str(bornDate))
				worksheet.write('D1', 'GENERO')
				# Check the gender
				gender = None
				if obj.gender == 'F':
					gender = 'FEMENINO'
				else:
					gender = 'MASCULINO'
				worksheet.write('D2', gender)
				# Check education level
				worksheet.write('E1', 'NIVEL_EDUCACIÓN')					
				educationLevel = None
				if obj.education_level == 'P':
					educationLevel = 'PRIMARIA'
				if obj.education_level == 'S':
					educationLevel = 'SECUNDARIA'
				if obj.education_level == 'U':
					educationLevel = 'UNIVERSITARIA'
				worksheet.write('E2', educationLevel)	
				# Init city
				city = None
				# Check the school
				rXschoolQS = ResoluterXSchool.objects.filter(resoluter = resoluterID)
				worksheet.write('H1', 'COLEGIO')
				worksheet.write('I1', 'GRADO')	
				grade = None					
				if rXschoolQS:
					rXschool = rXschoolQS[0]
					school = rXschool.school
					city = school.city
					worksheet.write('H2', school.name)
					grade = rXschool.grade.replace('g','')
					worksheet.write('I2', grade)
				else:
					worksheet.write('H2', 'NONE')
					worksheet.write('I2', 'NONE')
				# Check the university
				rXuniversityQS = ResoluterXUniversity.objects.filter(resoluter = resoluterID)				
				worksheet.write('J1', 'UNIVERSIDAD')
				worksheet.write('K1', 'FACULTAD')
				worksheet.write('L1', 'SEMESTRE')
				if rXuniversityQS:
					rXuniversity = rXuniversityQS[0]
					university = University.objects.get(id = rXuniversity.university.id)
					city = university.city
					worksheet.write('J2', university.institutionName)				
					worksheet.write('K2', rXuniversity.facult)
					grade = rXuniversity.semester.replace('g','')
					worksheet.write('L2', grade)
				else:
					worksheet.write('J2', 'NONE')				
					worksheet.write('K2', 'NONE')
					worksheet.write('L2', 'NONE')
				# Get locale info
				department = city.department	
				worksheet.write('F1', 'DEPARTAMENTO')
				worksheet.write('F2', department.name)
				worksheet.write('G1', 'CIUDAD')
				worksheet.write('G2', city.name)
				# Save file
				workbook.close()
				#-------------------------------------------------
				# Make XLSX to process sheet 1				
				workbook = xlsxwriter.Workbook(os.path.join(directory, 'session_0.xlsx'))
				worksheet = workbook.add_worksheet("Header")
				worksheet.write('A1', educationLevel)
				worksheet.write('B1', resoluterID)
				worksheet.write('C1', alias)
				# Get years
				now = datetime.datetime.now()
				rd = rdelta.relativedelta(now, bornDate)
				worksheet.write('D1', "{0.years}".format(rd))
				worksheet.write('E1', gender)
				worksheet.write('F1', grade)
				worksheet.write('G1', 'A2')
				worksheet.write('H1', 'B3')
				worksheet.write('I1', 'C3')
				worksheet.write('J1', '43')
				# Get session
				sessionQS = ProblemResolution.objects.filter(resoluter = resoluterID)				
				for ses in sessionQS:
					try:
						#-------------------------------------------------
						# Make XLSX to process sheet session
						worksheet = workbook.add_worksheet("Problem_" + str(ses.problem.id))								
						# Get attemps
						attempsQS = Attempt.objects.filter(resolution = ses).order_by('resolution')
						attempLimit = ses.problem.attempts_limit
						index = 1
						for attemp in attempsQS:
							if index <= attempLimit:					
								worksheet.write('A' + str(index), str(attemp.updated))
								worksheet.write('B' + str(index), index)
								worksheet.write('C' + str(index), attemp.result_error)
								worksheet.write('D' + str(index), attemp.var1_value)
								worksheet.write('E' + str(index), attemp.var2_value)
								worksheet.write('F' + str(index), 'V')
								worksheet.write('G' + str(index), 10)
								index = index + 1
							else:
								break
					except:
						print("Repite problem_" + str(ses.problem.id) + " for resoluter id: " + resoluterID)
				# Save file
				workbook.close()

		self.message_user(request,"La información fue generada [OK].")

		# Call java application
		print(PATH)
		proc = Popen(PATH, shell = True, close_fds = True)
		stdout, stderr = proc.communicate()
		print(proc.returncode)
		if proc.returncode == 0:
                	self.message_user(request, "Java application executed successfully [OK].")
                	print(stdout)
		else:
                	self.message_user(request, "Java application failed. Check logs for details.")
                	print(stderr)
		self.message_user(request,"Generación de resultados [OK].")

	# Audio process function
	def process_audio(self, request, queryset):
		# Define variables		
		audio = None		 
		r = sr.Recognizer()
		stopWords = set(stopwords.words("spanish"))
		BASE = os.path.abspath(os.path.join(os.path.dirname(__file__), '..', '..'))
		PATH = BASE
		
		# Process audio
		for obj in queryset:
			text = ''
			facts = []
			resoluterID = obj.resoluter_id

			# Check the capture folder
			directory = None
			try:
				directory = os.path.join(PATH, 'java', 'UranusCB-V2.0', 'data', 'capture', resoluterID)
				if not os.path.exists(directory):
					print(f"directory created in {directory}")
					os.mkdir(directory);
			except IOError as e:
				messages.warning(request, 'Problema E001: por favor contactar a soporte.')

			# Check the dictory
			if directory:
				# Process each audio
				for i in range(1,4):
					filtered = None					
					try:
						# Create the report file
						file = open(os.path.join(directory,'text-r' + resoluterID + '-p' + str(i) + '.txt'), "w+")

						# Process each question
						for j in range(1,4):		
							try:
								# Get audio		
								audioFile = sr.AudioFile(os.path.join(PATH, 'java', 'UranusCB-V2.0', 'data', 'audio', resoluterID.replace('-', ''), 'audio-r' + resoluterID + '-p' + str(i) + '-q' + str(j) + '.wav'))
                                #print("VUSACARARARA",audioFile,os.path.join(PATH, 'java', 'UranusCB-V2.0', 'data', 'audio', resoluterID.replace('-', ''), 'audio-r' + resoluterID + '-p' + str(i) + '-q' + str(j) + '.wav'))
								with audioFile as source:
									audio = r.record(source)
							except:
								messages.warning(request, 'Alerta 001: No se puede leer el archivo: ' + 'audio-r' + resoluterID + '-p' + str(i) + '-q' + str(j) + '.wav')
							if(audio):
								try:
									# Get text from audio					
									text = text + ' ' + r.recognize_google(audio, language = "es-PE")																		
								except:
									messages.warning(request, 'Problema E002: Servicio no disponible intente más tarde por favor.')
									break
						
						# Write the text into file report
						file.write('[TEXT]\r\n')
						file.write(text + "\r\n")					 
						# Do word tokenize
						file.write('[TOKENIZE]\r\n')
						words = word_tokenize(text)
						file.write(str(words) + "\r\n")
						# Filtered
						file.write('[FILTERED]\r\n')
						filtered = [w for w in words if not w in stopWords]
						file.write(str(filtered) + "\r\n")
						file.close()

						# Collect all facts
						facts = facts + filtered

					except:
						messages.warning(request, 'Problema E002: por favor contactar a soporte.')

			# Check facts
			if facts:
				# Create histogram file
				workbook = xlsxwriter.Workbook(os.path.join(directory, 'AIResult.xlsx'))
				worksheet = workbook.add_worksheet("Data")

				# Get data class patterns
				classTagQS = ClassTag.objects.all()
				if classTagQS:
					index = 1
					histo = []
					matcheCount = 0
					for cls in classTagQS:
						frequencyList = []
						worksheet.write('A' + str(index), 'PATTERNS')
						worksheet.write('B' + str(index), 'CLASS ' + cls.name)
						index = index + 1						
						for ptrn in cls.patterns:
							acum = 0
							for fact in facts:
								if ptrn == fact:
									acum = acum + 1
									matcheCount = matcheCount + 1
							frequencyList.append({
								'pattern': ptrn,
								'frequency': acum
							})
							worksheet.write('A' + str(index), ptrn)
							worksheet.write('B' + str(index), acum)
							index = index + 1
						histo.append({
							'class': cls.name,
							'frequencies': frequencyList
						})
						index = index + 2

					# Make the process
					wordCount = len(facts)
					worksheet = workbook.add_worksheet("Result")
					worksheet.write('A1', 'Amount of terms')
					worksheet.write('B1', wordCount)
					worksheet.write('A2', 'Amount of matches')
					worksheet.write('B2', matcheCount)
					worksheet.write('A3', 'Percentage of matches')
					worksheet.write('B3', (matcheCount / wordCount) * 100)
					worksheet.write('A6', 'CLASS')
					worksheet.write('B6', 'AVERAGE FREQUENCY')
					index = 7
					mayor = -1
					mayorClass = 'None'
					for hist in histo:
						worksheet.write('A' + str(index), hist['class'])
						acum = 0
						for ht in hist['frequencies']:
							acum = acum + ht['frequency']
						worksheet.write('B' + str(index), acum)
						if acum > mayor:
							mayor = acum
							mayorClass = hist['class']
						index = index + 1
					index = index + 2
					worksheet.write('A' + str(index), 'Prediction')
					worksheet.write('A' + str(index + 1), mayorClass)
					
					# Save file
					workbook.close()
					
		self.message_user(request,"Generación de resultados de esquema [OK].")			
		
	# Dowloand function
	def process_download(self, request, queryset):

		# Get path
		BASE = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
		print(BASE)
		target = os.path.join(BASE, 'tasks', 'static', 'assets', 'data.zip')
		PATH = os.path.abspath(os.path.join(os.path.dirname(__file__), '..', '..'))
		source = os.path.join(PATH, 'java', 'UranusCB-V2.0', 'data')

		# User message
		self.message_user(request,"Comprimiendo resultados...")

		# Data Zip
		fantasy_zip = zipfile.ZipFile(target, 'w')
		for folder, subfolders, files in os.walk(source):
			for file in files:
				fantasy_zip.write(os.path.join(folder, file), os.path.relpath(os.path.join(folder,file), source), compress_type = zipfile.ZIP_DEFLATED)
		fantasy_zip.close()

		# User message
		self.message_user(request,"Archivo de resultados [OK]")

		# Dowloandfile
		if os.path.exists(target):
			with open(target, 'rb') as fh:
				response = HttpResponse(fh.read(), content_type="application/.zip")
				response['Content-Disposition'] = 'inline; filename=' + os.path.basename(target)
				return response
		raise Http404


	export_as_csv.short_description = "Process Data"
	process_audio.short_description = "Process Audio"
	process_download.short_description = "Download"

@admin.register(Resoluter)
class TableAdmin(admin.ModelAdmin, ExportCsvMixin):
    actions = ["export_as_csv", "process_audio", "process_download"]

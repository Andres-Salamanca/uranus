# Import libreries
from django.shortcuts import render
from django.http import HttpResponse
from django.http import HttpResponseRedirect
from tasks.models import *
import json
from random import randint
from datetime import datetime
from os import system
import os

#============================================================
# WELCOME
#============================================================

# Index request
def index(request):	
	return render(request, 'tasks/welcome.html')

#============================================================
# REGISTER
#============================================================

# Register request
def register(request):
	context = {
		'idx' : None,
		'order' : [],
		'sessionID' : None
	}
	return render(request, 'tasks/register.html', context)

# Return departaments  
def getDepartaments(request):
	entities = []
	departmentsQS = Department.objects.all().order_by('name')
	for x in range(0,len(departmentsQS)):
		entities.append({
			'id' : departmentsQS[x].id,
			'name' : departmentsQS[x].name
		})			
	return HttpResponse(json.dumps(entities))

# Return the city list acoord to departament  
def getCities(request):
	dptID = request.POST['dptID']
	cities = []
	dptQS = Department.objects.filter(id = dptID)
	citiesQS = City.objects.filter(department = dptQS[0]).order_by('name')
	for y in range(0,len(citiesQS)):
		cities.append({
			'id' : citiesQS[y].code,
			'name' : citiesQS[y].name
		})			
	return HttpResponse(json.dumps(cities))

# Return the univercity list acoord to city
def getUniversities(request):
	rtn = []
	ctyID = request.POST['ctyID']
	citiesQS = City.objects.filter(code = ctyID)
	uvtQS = University.objects.filter(city = citiesQS[0]).order_by('institutionName')
	for y in range(0,len(uvtQS)):
		rtn.append({
			'id' : uvtQS[y].id,
			'name' : uvtQS[y].institutionName
		})			
	return HttpResponse(json.dumps(rtn))

# Return the school list acoord to city
def getSchools(request):
	rtn = []
	ctyID = request.POST['ctyID']
	citiesQS = City.objects.filter(code = ctyID)
	schQS = School.objects.filter(city = citiesQS[0]).order_by('name')
	for y in range(0,len(schQS)):
		rtn.append({
			'id' : schQS[y].id,
			'name' : schQS[y].name
		})			
	return HttpResponse(json.dumps(rtn))

# Register action
def registerAction(request):
	# Define partial varibles values
	gradePrimary = ''
	gradeSecundary = ''
	flt = ''
	ster = ''
	# Get variables from post method
	als = request.POST['alias']
	datepicker = request.POST['datepicker']
	gdr = request.POST['gender']
	level = request.POST['level']
	departament = request.POST['departament']
	city = request.POST['city']
	institute = request.POST['institute']
	# Check the partial variables
	if request.POST.get('grade_primary'):
		gradePrimary = request.POST['grade_primary']
	if request.POST.get('grade_secundary'):
		gradeSecundary = request.POST['grade_secundary']
	if request.POST.get('facult'):
		flt = request.POST['facult']
	if request.POST.get('semester'):	
		ster = request.POST['semester']
	# Set format to date
	dateSplit = datepicker.split('/')
	datepicker = ''
	for dateObj in dateSplit:
		datepicker = dateObj + '-' + datepicker
	datepicker = datepicker.strip('-') 
	# Check if resoluter exist
	resoluterID = datepicker + institute + gradePrimary + gradeSecundary + ster
	rstQS = Resoluter.objects.filter(resoluter_id = resoluterID)
	if len(rstQS) > 0:
		# Return response
		context = {
			'msg': 'No se permite realizar el experimento más de una vez. Intenta con otros datos.',
			'state' : 0,
			'session_id' : ''
		}
		return HttpResponse(json.dumps(context))
	# Create Resoluter entity instance	
	rlter = Resoluter (
		resoluter_id = resoluterID,
		alias = als,
		born_date = datepicker,
		gender = gdr,
		education_level = level
	)
	rlter.save()
	# Check education level
	if level == 'P':
		schoolObj = School.objects.get(id=institute)
		resoluterXSchool = ResoluterXSchool (
			resoluter = rlter,
			grade = gradePrimary,
			school = schoolObj
		)
		resoluterXSchool.save()
	elif level == 'S':
		schoolObj = School.objects.get(id=institute)
		resoluterXSchool = ResoluterXSchool (
			resoluter = rlter,
			grade = gradeSecundary,
			school = schoolObj
		)
		resoluterXSchool.save()
	else:
		universityObj = University.objects.get(id=institute)
		resoluterXUniversity = ResoluterXUniversity (
			resoluter = rlter,
			semester = ster,
			facult = flt,
			university = universityObj	 
		)
		resoluterXUniversity.save()
	# Create resoluter session
	ordr = []	
	rnd = randint(1, 3)
	ordr.append(rnd)
	if rnd == 1:
		rnd = randint(2, 3)
		ordr.append(rnd)
		if rnd == 2:
			ordr.append(3)
		else:
			ordr.append(2)
	elif rnd == 2:
		rnd = randint(1, 2)		
		if rnd == 1:
			ordr.append(rnd)
			ordr.append(3)
		else:
			ordr.append(3)
			ordr.append(1)
	elif rnd == 3:
		rnd = randint(1, 2)
		ordr.append(rnd)
		if rnd == 1:
			ordr.append(2)
		else:
			ordr.append(1)
	resoluterSession = ResoluterSession (
		resoluter = rlter,
		game_cursor = 0,
		order = ordr
	)
	resoluterSession.save()	

	# Return response
	context = {
		'msg': '',
		'state' : 1,
		'session_id' : resoluterSession.id
	}
	return HttpResponse(json.dumps(context))

#============================================================
# INTRO
#============================================================

# Introduction request
def intro(request):
	# Define variables
	sessionID = '-1'
	# Get session
	if request.POST.get('id'):
		sessionID = int(request.POST['id'])
	elif request.GET.get('id'):
		sessionID = int(request.GET['id'])

	context = {
		'session_id' : sessionID
	}
	return render(request, 'tasks/introduction.html', context)

#============================================================
# TUTO
#============================================================

# Tutorial request
def tuto(request):
	# Define variables
	sessionID = '-1'
	# Get session
	if request.POST.get('user-session'):
		sessionID = int(request.POST['user-session'])
	context = {
		'session_id' : sessionID
	}
	return render(request, 'tasks/tuto.html', context)

#============================================================
# CONTACT
#============================================================

# Contact request
def contact(request):
	return render(request, 'tasks/contact.html')	

# Contact action
def contactAction(request):
	nme = request.POST['name']
	eail = request.POST['email']
	sbject = request.POST['subject']
	mssage = request.POST['message']
	contact = ContactObj (
		name = nme,
		email = eail,
		subject = sbject,
		message = mssage
	)
	contact.save()
	return render(request, 'tasks/welcome.html')	

#============================================================
# PROBLEMS
#============================================================

# Problem action
def problem(request):
	# Define variables
	view = ''
	session = None
	# Get session
	sessionID = request.POST['user-session']
	# Check the session ID
	if sessionID == '-1':
		return HttpResponseRedirect('/registro')
	sessionID = int(sessionID)		
	sesQS = ResoluterSession.objects.filter(id = sessionID)	
	# Check the session
	if len(sesQS) > 0:
		session = sesQS[0]
	# Selects the view
	problemsQS = Problem.objects.filter(id = session.order[session.game_cursor])
	problem = problemsQS[0]
	view = 'tasks/' + problem.view_class
	# Log of the start resolution
	now = datetime.now()
	prQR = ProblemResolution.objects.filter(resoluter = session.resoluter)
	if len(prQR) == 0:
		pr = ProblemResolution (
			resoluter = session.resoluter,
			problem = problem,
			start_resolution = now,
			end_resolution = now
		)
		pr.save()
	else:
		pr = prQR[0]

	# Generate the goal
	goal = randint(70, 90)

	# Return view
	context = {
		'session_id' : session.id,
		'resolution_id' : pr.id,
		'problem' : problem,		
		'representation_flag' : 0,
		'file_name': None,
		'firsht_flag': 0,
		'goal': goal
	}
	return render(request, view, context)

# Attempt action
def attempt(request):
	# Define variables
	session = None
	resolution = None
	# Get variables
	var1 = int(request.POST['var1'])
	var2 = int(request.POST['var2'])
	resError = float(request.POST['res_error'])
	# Get session
	sessionID = int(request.POST['user-session'])
	sesQS = ResoluterSession.objects.filter(id = sessionID)	
	# Check the session
	if len(sesQS) > 0:
		session = sesQS[0]	
	# Get resolution
	resolutionID = int(request.POST['resolution-id'])
	resQS = ProblemResolution.objects.filter(id = resolutionID)	
	# Check the resolution
	if len(resQS) > 0:
		resolution = resQS[0]
	# Create a attempt	
	if not (var1 == 0 and var2 == 0):
		attempt = Attempt(
			resolution = resolution,
			var1_value = var1,
			var2_value = var2,
			result_error = resError,
			impr_pant = 'None'
		)
		attempt.save()
	
	# Response
	return HttpResponse(json.dumps({'result': 'OK'}))

# New problem action
def newProblem(request):
	# Define variables
	view = ''
	firsht = 0
	session = None
	fileName = None
	resolution = None
	representationFlag = 0
	# Get session
	sessionID = int(request.GET['ses'])
	sesQS = ResoluterSession.objects.filter(id = sessionID)	
	# Check the session
	if len(sesQS) > 0:
		session = sesQS[0]
		# Update session and resolution	
		session.game_cursor = session.game_cursor + 1	
		session.save()
	# Get resolution
	resolutionID = int(request.GET['res'])
	resQS = ProblemResolution.objects.filter(id = resolutionID)	
	# Check the resolution
	if len(resQS) > 0:
		resolution = resQS[0]				
		resolution.end_resolution = datetime.now()
		resolution.save()
	# Check the cursor	
	if session.game_cursor < 3:
		# Selects the view
		problemsQS = Problem.objects.filter(id = session.order[session.game_cursor])
		problem = problemsQS[0]
		view = problem.name
		view = 'tasks/' + problem.view_class
		# Log of the start resolution
		now = datetime.now()
		pr = ProblemResolution (
			resoluter = session.resoluter,
			problem = problem,
			start_resolution = now,
			end_resolution = now
		)
		pr.save()
		resolutionID =  pr.id
		firsht = 0
	else:
		if session.game_cursor == 6:
			return HttpResponseRedirect('/')
		if session.game_cursor == 3:
			firsht = 1
		# Selects the view
		problemsQS = Problem.objects.filter(id = session.order[session.game_cursor - 3])
		problem = problemsQS[0]
		view = problem.name
		view = 'tasks/' + problem.view_class
		# Set true representation mode
		representationFlag = 1
		resolutionID = -1
		# Log representation
		fileName = 'audio-r' + str(session.resoluter.resoluter_id) + '-p' + str(problem.id)
		rp = Representation(
			resoluter = session.resoluter,
			problem = problem,
    		path_audio = fileName,
    		text = ''
		)
		rp.save()

	# Generate the goal
	goal = randint(70, 90)

	# Return view
	context = {
		'session_id' : session.id,
		'resolution_id' : resolutionID,
		'problem' : problem,
		'representation_flag' : representationFlag,
		'file_name' : fileName,
		'firsht_flag': firsht,
		'goal': goal
	}
	return render(request, view, context)

#============================================================
# AUDIO
#============================================================

# Define path file
PATH = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
PATH = PATH.replace('uranus', '')	

# Recieve audio from host
def sendAction(request):
	handle_uploaded_file(request.FILES['.wav'], request.POST['name'])
	resp = {'respose': 'OK'}
	return HttpResponse(json.dumps(resp))

# Handle the uploaded file
def handle_uploaded_file(f, name):
	# Get folder
	folder = ''
	aux = name.replace('audio-r', '')
	for c in aux:
		if c != 'p' and c != '-':
			folder = folder + c
		else:
			if c == 'p':
				break
	# Check the report folder
	directory = None
	try:
		directory = os.path.join(PATH, 'java', 'UranusCB-V2.0', 'data', 'audio', folder)
		if not os.path.exists(directory):
			os.mkdir(directory);
		with open(os.path.join(directory, name), 'wb+') as destination:
			for chunk in f.chunks():
				destination.write(chunk)
	except IOError as e:
		print(str(e))
		pass

	
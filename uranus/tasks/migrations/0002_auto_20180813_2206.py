# Generated by Django 2.1 on 2018-08-14 03:06

from django.db import migrations

import os
import json

def seed(apps, schema_editor):
	
	Department = apps.get_model('tasks', 'Department')
	City = apps.get_model('tasks', 'City')
	School = apps.get_model('tasks', 'School')
	University = apps.get_model('tasks', 'University')

	BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
	dataLoaded = open(os.path.join(BASE_DIR, 'seed', 'colegios.json'))   
	data = json.load(dataLoaded)
	
	print ('')
	print ('[Procesing Schools]')

	for x in range(0, len(data)):
		dta = data[x]		
		print ('Procesing: [', x+1, '] ',dta['nombre'])
		dptObj = Department.objects.filter(id = str(dta['codigo_departamento']))
		if not dptObj:
			department = Department (
				id = str(dta['codigo_departamento']),
				name = dta['departamento']
			)
			department.save()
			dptObj = department
		else:
			dptObj = dptObj[0]

		ctyObj = City.objects.filter(code = str(dta['codigo_municipio']))		
		if not ctyObj:
			city = City(
				name = dta['municipio'],
				department = dptObj,
				code = str(dta['codigo_municipio'])
			)
			city.save()
			ctyObj = city
		else:
			ctyObj = ctyObj[0]

		school = School(
			city = ctyObj,
			name = dta['nombre'],
			address = dta['direccion'],
			phone = str(dta['telefono']),
			sector = dta['sector'],
			gender = dta['genero'],
			zone = dta['zona'],
			levels = dta['niveles'],
			Journeys = dta['jornadas'],
			Email = dta['correo']
		)
		school.save()			 

	dataLoaded.close()

	dataLoaded = open(os.path.join(BASE_DIR, 'seed', 'universidades.json'))   
	data = json.load(dataLoaded)

	print ('')
	print ('[Procesing Universities]')
	
	for x in range(0, len(data)):
		dta = data[x]

		print ('Procesing: ', dta['codigoinsitucion'])

		cty = City.objects.filter(code = dta['codigomunicipio'])

		if cty:
			university = University (
				city = cty[0],
				institutionCode = dta['codigoinsitucion'],		
				institutionName = dta['nombreinstitucion'],		
				institutionSource = dta['nombreorigeninstitucional']
			)		
			university.save()
		else:
			print ('ERRORRRRRRRRRR: ', dta['codigoinsitucion'])

	dataLoaded.close()


class Migration(migrations.Migration):

    dependencies = [
        ('tasks', '0001_initial'),
    ]

    operations = [
    	migrations.RunPython(seed),
    ]

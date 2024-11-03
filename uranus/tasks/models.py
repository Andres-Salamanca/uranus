from django.db import models
from django.contrib.postgres.fields import ArrayField
import json
class Department(models.Model):
	name = models.CharField(max_length=100)

class City(models.Model):
	department = models.ForeignKey(Department, on_delete=models.CASCADE)
	name = models.CharField(max_length=100)
	code = models.CharField(max_length=7)

class University(models.Model):
	city = models.ForeignKey(City, on_delete=models.CASCADE)
	institutionCode = models.CharField(max_length=100)		
	institutionName = models.CharField(max_length=100)		
	institutionSource = models.CharField(max_length=100)

class School(models.Model):
	city = models.ForeignKey(City, on_delete=models.CASCADE)
	name = models.CharField(max_length=150)
	address = models.CharField(max_length=100)
	phone = models.CharField(max_length=50)
	sector = models.CharField(max_length=50)
	gender = models.CharField(max_length=20)
	zone = models.CharField(max_length=50)
	levels = models.CharField(max_length=100)
	Journeys = models.CharField(max_length=100)
	Email = models.CharField(max_length=100)

class Resoluter(models.Model):
	resoluter_id = models.CharField(max_length=50, primary_key=True)
	alias = models.CharField(max_length=50)
	born_date = models.DateField()
	gender = models.CharField(max_length=1)
	education_level = models.CharField(max_length=1)

class ResoluterXUniversity(models.Model):
	resoluter = models.ForeignKey(Resoluter, on_delete=models.CASCADE)
	university = models.ForeignKey(University, on_delete=models.CASCADE)
	semester = models.CharField(max_length=5)
	facult = models.CharField(max_length=100)

class ResoluterXSchool(models.Model):
	resoluter = models.ForeignKey(Resoluter, on_delete=models.CASCADE)
	school = models.ForeignKey(School, on_delete=models.CASCADE)
	grade = models.CharField(max_length=5)

class ResoluterSession(models.Model):
    resoluter = models.ForeignKey(Resoluter, on_delete=models.CASCADE)
    game_cursor = models.IntegerField()
    order = models.TextField(blank=True)  # Change to TextField

    def save(self, *args, **kwargs):
        # Serialize the list to a JSON string
        if isinstance(self.order, list):
            self.order = json.dumps(self.order)
        super().save(*args, **kwargs)

    def get_order(self):
        # Deserialize the JSON string back to a list
        return json.loads(self.order) if self.order else []

class Problem(models.Model):
	name = models.CharField(max_length=50)
	view_class = models.CharField(max_length=50)
	title = models.CharField(max_length=50)
	short_description = models.CharField(max_length=200)
	description = models.CharField(max_length=500)
	preview_image = models.CharField(max_length=50)
	var1_name = models.CharField(max_length=20)
	var2_name = models.CharField(max_length=20)
	var1_min = models.IntegerField()
	var1_max = models.IntegerField()
	var2_min = models.IntegerField()
	var2_max = models.IntegerField()
	goal = models.DecimalField(max_digits=5, decimal_places=2)
	error_allowed = models.DecimalField(max_digits=5, decimal_places=4)
	scale = models.IntegerField()
	attempts_limit = models.IntegerField()

class ProblemResolution(models.Model):
	resoluter = models.ForeignKey(Resoluter, on_delete=models.CASCADE)
	problem = models.ForeignKey(Problem, on_delete=models.CASCADE)
	start_resolution = models.DateTimeField()
	end_resolution = models.DateTimeField()

class Attempt(models.Model):
	resolution = models.ForeignKey(ProblemResolution, on_delete=models.CASCADE)
	updated = models.DateTimeField(auto_now=True)
	var1_value = models.IntegerField()
	var2_value = models.IntegerField()
	result_error = models.DecimalField(max_digits=10, decimal_places=3)
	impr_pant = models.CharField(max_length=50)

class Representation(models.Model):
	resoluter = models.ForeignKey(Resoluter, on_delete=models.CASCADE)
	problem = models.ForeignKey(Problem, on_delete=models.CASCADE)
	path_audio = models.CharField(max_length=100)
	text = models.CharField(max_length=200)

class ClassTag(models.Model):
	name = models.CharField(max_length=50)
	patterns = models.CharField(max_length=255, blank=True)

class ContactObj(models.Model):
	name = models.CharField(max_length=50)
	email = models.CharField(max_length=50)
	subject = models.CharField(max_length=100)
	message = models.CharField(max_length=200)
	

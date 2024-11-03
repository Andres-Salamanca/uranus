from django.db import migrations

def seed(apps, schema_editor):
	# Get reference of entity.
	Problem = apps.get_model('tasks', 'Problem')
	# Make problem 1
	problem = Problem (
		name = "Movimiento Rectilíneo",
		view_class = "linear-move.html",
		title = "Halla la distancia hasta la meta",
		short_description = "Haz que el niño alcance la meta. Da valores a la velocidad con la que corre y el tiempo que dura para que llegue a la meta.",
		description = "El problema de movimiento rectilíneo se da en el contexto de una carrera llevada a cabo por un  maratonista (el niño corredor como se puede apreciar en la imagen) que con ayuda de los controles de variable se debe determinar la velocidad que debe imprimirse al niño  y el tiempo que debe durar el niño corriendo para cubrir una distancia hasta llegar al punto exacto de la meta. Si el niño se pasa de la meta se considera como un intento inválido ya que el niño debe quedar justo en la meta.",
		preview_image = "tasks/img/runPreview.png",
		var1_name = "Velocidad",
		var2_name = "Tiempo",
		var1_min = 0,
		var1_max = 100,
		var2_min = 0,
		var2_max = 100,
		goal = 0.7,
		error_allowed = 0.05,
		scale = 10,
		attempts_limit = 15
	)
	problem.save()
	# Make problem 2
	problem = Problem (
		name = "Movimiento de un Fluido",
		view_class = "fluid-move.html",
		title = "Halla el nivel de agua adecuado",
		short_description = "Haz que se llene el jarrón hasta la marca roja. Da valores a la cantidad de agua que sale por la llave y el tiempo que dura abierta para que el agua llegue hasta la marca.",
		description = "El problema de movimiento de un fluido se da en el contexto de una cocina en donde se debe determinar la apertura de la llave y el tiempo que debe durar la llave abierta para dar con el nivel de agua requerido en el lavaplatos. Si el nivel del agua sobrepasa el nivel requerido el intento se considerara inválido ya que se requiere que el agua llegue justo al nivel requerido.",
		preview_image = "tasks/img/fluidPreview.png",
		var1_name = "Apertura",
		var2_name = "Tiempo",
		goal = 0.7,
		var1_min = 0,
		var1_max = 100,
		var2_min = 0,
		var2_max = 100,
		error_allowed = 0.05,
		scale = 10,
		attempts_limit = 15
	)
	problem.save()
	# Make problem 3
	problem = Problem (
		name = "Balanza de Pesos",
		view_class = "balance.html",
		title = "Halla el punto de equilibrio",
		short_description = "Da valores a la distancia y al peso del niño para que el balancín quede equilibrado, así como muestra la figura.",
		description = "El problema de balanza de pesos se da en el contexto de un parque de diversiones en donde se debe  determinar el peso del niño y la distancia respecto al pivote (eje de rotación) para que la balanza quede horizontalmente en equilibrio.",
		preview_image = "tasks/img/balancePreview.png",
		var1_name = "Distancia",
		var2_name = "Peso",
		goal = 0.5,
		var1_min = 0,
		var1_max = 100,
		var2_min = 0,
		var2_max = 100,
		error_allowed = 0.05,
		scale = 10,
		attempts_limit = 15
	)
	problem.save()

class Migration(migrations.Migration):

    dependencies = [
        ('tasks', '0003_auto_20180905_1117'),
    ]

    operations = [
    	migrations.RunPython(seed),
    ]

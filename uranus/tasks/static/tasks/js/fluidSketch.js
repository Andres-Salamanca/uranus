
let snowflakes = [];
var fluidX;
var fluidY;
var fluidWidth;
var fluidHeight;
var tangX1;
var tangWidth;
var tangHeight;
var attempts = 1;
var sharedHeight = 0;
var showWater = false;
var pipe1;
var pipe2;
var nomore = false;
var msgFalg = true;

function preload() {
	bg = loadImage("static/tasks/img/fluido3.jpg");
	pipe1 = loadImage("static/tasks/img/pipe1.png");
	pipe2 = loadImage("static/tasks/img/pipe2.png");	
}

function setup() {
	var box = $('.canvas-box'); 
	w = $(box[index]).width();
	var h = w * 0.582;
	var canvas = createCanvas(w, h);
	canvas.parent('fluid-move-canvas-obj');	
	fluidY = h * 0.570;//588
	fluidHeight = h * 0.27;//0.21
	tangWidth = w * 0.21;
	tangHeight = 2; 
	limit = h * 0.19;
	goal = limit * goal;
	error = limit * error;
	slope = limit / (dpdMX * constMX);
	x = 0;

	if (representationFlag) {
		var constVarRangeslider = $(".slider-range-constVar")[0];
		var dpdVarRangeslider = $(".slider-range-dpdVar")[0];
		$(constVarRangeslider).val(70);
		$(dpdVarRangeslider).val(50);
		dpdVarGauge.update({value: '70'});
		gaugePS.update({value: '50'});	
		topVar = 70;
		constVar = 50;	
	}
}

function draw() {
	tangX1 = (w / 2) - (tangWidth * 1.17);
	fill(240);
	noStroke();
	background(bg);

	fluidWidth = (35 / constMX) * (constVar - 1);
	fluidX = (w * 0.39) - fluidWidth;

	image(pipe2,width * 0.37 - fluidWidth, height * 0.481, 120, 60);
	image(pipe1,width * 0.39, height * 0.495, 70, 70);

	fill(0);
	stroke(0);
	fill(0, 0, 255);
	stroke(0, 0, 255);
	
	if (runFlag) {

		if (showWater) {		 
			sharedHeight = fluidHeight + fluidY - x;
			rect(fluidX, fluidY, fluidWidth, sharedHeight - fluidY);		
		}
		rect(tangX1 + 2, sharedHeight, tangWidth, tangHeight + x);	
		fill(92, 150, 250);
		noStroke();

		if (!nomore) {
			let t = frameCount / 60;
			for (var i = 0; i < random(5); i++) {
				snowflakes.push(new Snowflake());
			}
			for (let flake of snowflakes) {
				flake.update(t);
				flake.display();
			}
		}
		showWater = true;
		dpdVar += 1;
		if (!nomore) {
			x = slope * dpdVar * constVar;
		}
		if (x >= limit || dpdVar >= topVar) {
			nomore = true;
			setTimeout(evalFluid(), 2000);
		}
	}
	fill(255, 0, 0);
	noStroke();
	rect(tangX1, (fluidHeight + fluidY) - goal, tangWidth, 5);
	fill(215,215,215,63);
	noStroke()
	rect(tangX1, fluidHeight + fluidY - limit, tangWidth + 5, limit + 5);
}

function windowResized() {
	var box = $('.canvas-box'); 
	w = $(box[index]).width();
	var h = w * 0.582;
	resizeCanvas(w, h);
	fluidY = h * 0.35;
	fluidHeight = h * 0.625;
	tangWidth = w * 0.565;
	tangHeight = 2; 
	limit = h * 0.23;
	goal = limit * goal;
	error = limit * error;
	slope = limit / (dpdMX * constMX);
}

function fluidAgain() {	
	nomore = false;
	runFlag = false;
	dto = {
		'user-session' : sessionID,
		'resolution-id' : resolutionID,
		'var1': constVar,
		'var2': topVar,
		'res_error': ( ( (topVar * constVar) / ((goal/limit) * spaceDist) ) * 100 )
	};
	current = $(this);
	requestAjax('attempt',dto, function() {
		if (!success && attempts <= attemptsLimit) {
			attempts++;
			var constVarRangeslider = $(".slider-range-constVar")[index];
			var dpdVarRangeslider = $(".slider-range-dpdVar")[index];
			$(constVarRangeslider).val(0);
			$(dpdVarRangeslider).val(0);
			dpdVarGauge.update({value: '0'});
			gaugePS.update({value: '0'});	
			x = 0;
			dpdVar = 0;
			constVar = 0;
			sharedHeight = fluidHeight + fluidY;
			topVar = 0;
			constVar = 0;
			$(".attempt-action").removeClass('no-active');
		} else {
			window.location="/nuevo-problema?ses=" + sessionID + '&res=' + resolutionID;
		}
		current.dialog("close");	
	}, msgError);
}

function evalFluid() {
	if (representationFlag) {
		if (msgFalg) {
			msgFalg = false;
			$(".des-box").empty();				

			$(".des-box").append('Si te dijiera que en un segundo intento tendrías que poner los valores para que llegue a l nivel. ¿Qué valores colocarías? Da clic en grabar y responde la pregunta en voz alta y luego da clic en terminar por favor.');
			$("#m2").show();
		}
	} else {	
		if (Math.abs(goal - x) < error) {
			$("#msg-modal").empty();
			$("#msg-modal").dialog('option', 'title', '¡Muy Bien!');
			$("#msg-modal").append('Continua con la siguiente tarea.');
			$("#msg-modal").dialog("open");
			success = true;
		} else {
			$("#msg-modal").empty();
			$("#msg-modal").dialog('option', 'title', 'No lo lograste');				
			$("#msg-modal").append('¡Intenta de nuevo!');
			$("#msg-modal").dialog("open");
			success = false;		
		}
	}
	showWater = false;
}

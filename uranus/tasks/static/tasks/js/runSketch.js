var boyImg1;
var boyObj;
var attempts;
var startImg;
var finishImg;
var wateFlag;


function preload() {
	bg = loadImage("static/tasks/img/run2.jpg");	
	boyImg = [];
	boyImg.push(loadImage("static/tasks/img/runing/1.png"));
	boyImg.push(loadImage("static/tasks/img/runing/2.png"));
	boyImg.push(loadImage("static/tasks/img/runing/3.png"));
	boyImg.push(loadImage("static/tasks/img/runing/4.png"));
	boyImg.push(loadImage("static/tasks/img/runing/5.png"));
	boyImg.push(loadImage("static/tasks/img/runing/6.png"));
	boyImg.push(loadImage("static/tasks/img/runing/7.png"));
	boyImg.push(loadImage("static/tasks/img/runing/8.png"));	
	startImg = loadImage("static/tasks/img/start.png");
	finishImg = loadImage("static/tasks/img/finish.png");
}

function setup() {
	var box = $('.canvas-box');
	w = $(box[index]).width();
	y = w * 0.582;
	var canvas = createCanvas(w, y);
	canvas.parent('linear-move-canvas-obj');
	boyObj = new Element(boyImg, x, y * 0.8, 70);
	x0 = w * 0.01;
	x = x0;
	limit = (w * 0.9) - x0;
	goal = limit * goal;
	error = limit * error;
	slope = limit / (dpdMX * constMX);
	y = height * 0.7;
	attempts = 1;
	wateFlag = true;

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
	background(bg);
	image(startImg, 1, y - 10, 100, 70);
	image(finishImg, goal + 20, y + 50, 70, 50);	
	if (wateFlag) {
		boyObj.update(x, y *(1.05), 70, true, constVar);
	} else {
		boyObj.update(x, y *(1.05), 70, true, 0);	
	}
	boyObj.display();
	if (runFlag) {		
		dpdVar += 1;
		x = (slope * dpdVar * constVar) + x0;
		if (x >= (limit + x0) || dpdVar >= topVar) {
			wateFlag = false;
			runFlag = false;
			$("#msg-modal").empty();
			setTimeout(runEval, 1000);
		}
	}
}

function windowResized() {
	var box = $('.canvas-box'); 
	width = $(box[index]).width();
	height = $(box[index]).height();
	w = $(box[index]).width();
	y = w * 0.582;
	var canvas = createCanvas(width, y);
	x0 = w * 0.05;
	x = x0;
	limit = (w * 0.9) - x0;
	goal = limit * goal;
	error = limit * error;
	slope = limit / (dpdMX * constMX);
}

function runAgain() {
	dto = {
		'user-session' : sessionID,
		'resolution-id' : resolutionID,
		'var1': constVar,
		'var2': dpdVar,
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
			x = x0;
			dpdVar = 0;		
			boyObj.update(x, y, 100, runFlag);
			boyObj.display();
			wateFlag = true;
			topVar = 0;
			constVar = 0;
			$(".attempt-action").removeClass('no-active');
		} else {
			window.location="/nuevo-problema?ses=" + sessionID + '&res=' + resolutionID;
		}	
		current.dialog("close");	
	}, msgError);
}

function runEval() {
	if (representationFlag) {
		$(".des-box").empty();				
		$(".des-box").append('Si te dijiera que en un segundo intento tendrías que poner los valores para que llegue a la meta. ¿Qué valores colocarías? Da clic en grabar y responde la pregunta en voz alta y luego da clic en terminar por favor.');
		$("#m2").show();
	} else {
		if (Math.abs(goal - (x-x0)) < error) {
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
}

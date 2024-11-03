var boyImg;
var baseImg;
var supportImg;
var barrierImg;
var questionImg;
var lognBarrier;
var constVMAX = 180;
var lPosition = 0;
var dpdPARM = 0.01;
var attempts = 1;
var volumen = 0;
var balanSTOC = 1;
var showSupport = true;

function preload() {
	bg = loadImage("static/tasks/img/park.jpg");
	boyImg = loadImage("static/tasks/img/boy.png");
	baseImg = loadImage("static/tasks/img/stage.png");
	barrierImg = loadImage("static/tasks/img/barrier.png");
	supportImg = loadImage("static/tasks/img/support.png");
	questionImg = loadImage("static/tasks/img/question.png");
}

function setup() {
	var box = $('.canvas-box'); 
	w = $(box[0]).width();
	var h = w * 0.582;
	var canvas = createCanvas(w, h);
	canvas.parent('balance-move-canvas-obj');	
	x = 0;
	limit = 30;

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
	lognBarrier = width * 0.5;
	constVMAX = lognBarrier * 0.5;
	image(baseImg,width * 0.5 - (width * 0.2/2), height * 0.61, width * 0.2, height * 0.2);	
	lPosition = (constVMAX / constMX) * (constVar - 1);
	volumen = (0.2 / 100) * (topVar - 1);
	translate( width * 0.5, height * 0.60);
	rotate(PI / 180 * x);
	imageMode(CENTER);	
	image(barrierImg,0, 0, lognBarrier, 21);
	image(questionImg, -1 * height * 0.21, -1 * height * 0.05, width * 0.25, width * 0.05);
	if (showSupport) {
		if (!(x > -limit && x < limit)) {
			balanSTOC = balanSTOC * (-1);			
		}
		x += 0.5 * balanSTOC;
		image(boyImg, 250, -40, 200, 200);
	} else {
		image(boyImg, lPosition, -1 * height * volumen * 0.3, width * volumen, width * volumen);
	}
	imageMode(CORNER);	
	if (runFlag) {
		showSupport = false;
		// 0 = p2*L2 - p1*La
		if (Math.abs(goal - (topVar * constVar)) < error) {
			evalBalance(true);
		} else {
			if (x > -limit && x < limit) {
				if ((topVar * constVar) > goal) {
					x += 0.5;
				} else {
					x -= 0.5;
				}
			} else {
				evalBalance(false); 
			}
		}	
	}
}

function windowResized() {
	var box = $('.canvas-box'); 
	w = $(box[0]).width();
	var h = w * 0.582;
	resizeCanvas(w, h);
}

function balanceAgain() {
	dto = {
		'user-session' : sessionID,
		'resolution-id' : resolutionID,
		'var1': constVar,
		'var2': topVar,
		'res_error': (((topVar * constVar) / goal) * 100)
	};
	current = $(this);
	requestAjax('attempt',dto, function() {
		if (!success && attempts <= attemptsLimit) {
			attempts++;
			var constVarRangeslider = $(".slider-range-constVar")[0];
			var dpdVarRangeslider = $(".slider-range-dpdVar")[0];
			$(constVarRangeslider).val(0);
			$(dpdVarRangeslider).val(0);
			dpdVarGauge.update({value: '0'});
			gaugePS.update({value: '0'});	
			x = 0;	
			topVar = 0;
			constVar = 0;
			$(".attempt-action").removeClass('no-active');		
		} else {
			window.location="/nuevo-problema?ses=" + sessionID + '&res=' + resolutionID;				
		}
		current.dialog("close");	
	}, msgError);
}

function evalBalance(flag) {
	if (representationFlag) {
		$(".des-box").empty();
		$(".des-box").append('Si te dijiera que en un segundo intento tendrías que poner los valores para que llegue a equilibrio. ¿Qué valores colocarías? Da clic en grabar y responde la pregunta en voz alta y luego da clic en terminar por favor.');
		$("#m2").show();		
	} else {
		if (!flag) {				
			$("#msg-modal").empty();
			$("#msg-modal").dialog('option', 'title', 'No lo lograste');				
			$("#msg-modal").append('¡Intenta de nuevo!');
			$("#msg-modal").dialog("open");
			success = false;
		} else {
			$("#msg-modal").empty();
			$("#msg-modal").dialog('option', 'title', '¡Muy Bien!');
			$("#msg-modal").append('Continua con la siguiente tarea.');
			$("#msg-modal").dialog("open");
			success = true;
		}
	}
	runFlag = false;
}

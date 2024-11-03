var bg;
var x, y;
var boyImg1;
var boyImg2;
var boyObj;

var runFlag = false;

var xLimit;
var time = 0;
var velocity;
var m = 0;
var tTOP;

function setup() {
	var w = 400;
	var h = 400;
	var canvas = createCanvas(w, h);
	xLimit = w - 10;
	canvas.parent('linear-move-canvas-obj');
	x = 10;
	y = height - 150;
}

function draw() {

	background(255);	
	stroke(50);
  	fill(100);
  	ellipse(x, y, 24, 24);

	if (runFlag) {
	  	

		time += 1;
		x = m * ( (time * velocity) - 1 );

		console.log(x);

		if (x >= xLimit || time >= tTOP) {
			runFlag = false;
		}
	}

}


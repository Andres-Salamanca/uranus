var logoImgR;

function preload() {
  logoImgR = loadImage("static/tasks/img/logoImgR.jpg");
}

function setup() {
  var canvas = createCanvas(250, 250, WEBGL);
  canvas.parent('splash-canvas-obj');
}

function draw() {
  background(255);

  var locX = mouseX - height / 2;
  var locY = mouseY - width / 2;

  push();
  translate((width / 2) - 125, 0, 0);
  rotateZ(frameCount * 0.02);
  rotateX(frameCount * 0.02);
  texture(logoImgR);
  box(100, 100, 100);
  pop();
}

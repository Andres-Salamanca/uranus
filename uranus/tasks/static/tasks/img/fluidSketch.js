let snowflakes = []; // array to hold snowflake objects

var fluidX = 100;
var fluidY = 100;
var fluidWidth = 100;
var fluidHeight = 100;

var tangX1 = 50;
var tangY1 = 300;
var tangWidth = 200;
var tangHeight = 400;

var sharedHeight = 0;

var x = 0;
var xLimit;
var time = 0;
var velocity;
var m = 0;
var tTOP;

var runFlag = false;

function setup() {
  var w = 400;
  var h = 400;
  var canvas = createCanvas(w, h);
  canvas.parent('linear-move-canvas-obj');
  fill(240);
  noStroke();
}

function draw() {

  background(255);

  fill(0, 0, 255);
  stroke(0, 0, 255); 

  sharedHeight = fluidHeight + 50 - x;

  rect(fluidX, fluidY - 50, fluidWidth, sharedHeight);

  rect(tangX1, sharedHeight + 50, tangWidth, tangHeight);

  fill(92, 150, 250);
  noStroke();

  let t = frameCount / 60; // update time

  // create a random number of snowflakes each frame
  for (var i = 0; i < random(5); i++) {
    snowflakes.push(new snowflake()); // append snowflake object
  }

  // loop through snowflakes with a for..of loop
  for (let flake of snowflakes) {
    flake.update(t); // update snowflake position
    flake.display(); // draw snowflake
  }

  if (runFlag) {

    time += 1;
    x = m * ( (time * velocity) - 1 );

    console.log(x);

    if (x >= xLimit || time >= tTOP) {
      runFlag = false;
    }


  }

}

// snowflake class
function snowflake() {
  // initialize coordinates
  this.posX = 0;
  this.posY = random(-50, 0);
  this.initialangle = random(0, 2 * PI);
  this.size = random(2, 5);

  // radius of snowflake spiral
  // chosen so the snowflakes are uniformly spread out in area
  this.radius = sqrt(random(pow(fluidWidth / 2, 2)));

  this.update = function(time) {
    // x position follows a circle
    let w = 0.6; // angular speed
    let angle = w * time + this.initialangle;
    this.posX = fluidWidth / 2 + this.radius * sin(angle);

    // different size snowflakes fall at slightly different y speeds
    this.posY += pow(this.size, 0.5);

    // delete snowflake if past end of screen
    if (this.posY > (fluidHeight - x)  ) {
      let index = snowflakes.indexOf(this);
      snowflakes.splice(index, 1);
    }
  };

  this.display = function() {
    ellipse(this.posX + fluidX, this.posY + fluidY, this.size);
  };
}

function Element(imgs, x, y, z) {

	this.x = x;
	this.y = y;
	this.z = z;
	this.vel = 100;
	this.counter = 0;
	this.imgs = imgs;
	this.turn = 0;
	this.runFlag = false;

	this.display = function() {
		if (!this.runFlag) {
			image(this.imgs[0], this.x, this.y, this.z, this.z);
		} else {
			image(this.imgs[this.turn], this.x, this.y, this.z, this.z);
			if (this.vel !=0) {
				this.counter += 5;
				if (this.counter > (100 - this.vel) ) {
					this.turn++;
					this.counter = 0;
				}

				if (this.turn > 7) {
					this.turn = 0;
				}
			}
		}
	}

	this.update = function(x, y, z, runFlag, vel) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.vel = vel;
		this.runFlag = runFlag;
	}

}

function Snowflake() {

	this.posX = 0;
	this.posY = random(0, 0);
	this.initialangle = random(0, 2 * PI);
	this.size = random(2, 5);
	this.radius = sqrt(random(pow(fluidWidth / 2, 2)));

	this.update = function(time) {
		let w = 0.6;
		let angle = w * time + this.initialangle;
		this.posX = fluidWidth / 2 + this.radius * sin(angle);
		this.posY += pow(this.size, 0.5);
		if (this.posY > (fluidHeight - x)  ) {
			let index = snowflakes.indexOf(this);
			snowflakes.splice(index, 1);
		}
	};

	this.display = function() {
		ellipse(this.posX + fluidX, this.posY + fluidY, this.size);
	};

}

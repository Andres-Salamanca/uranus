function preload() {
	switch(index) {
		case 0:
			runPreload();
		break;			
		case 1:
			fluidPreload();
		break;
		case 2:
			balancePreload();
		break;
	}
}

function setup() {
	switch(index) {
		case 0:
			runSetup();
		break;
		case 1:
			fluidSetup();
		break;
		case 2:
			balanceSetup();
		break;
	}
}

function draw() {
	switch(index) {
		case 0:
			runDraw();
		break;
		case 1:
			fluidDraw();
		break;
		case 2:
			balanceDraw();
		break;
	}
}

function windowResized() {
	switch(index) {
		case 0:
			runWindowResized();
		break;
		case 1:
			fluidWindowResized();
		break;
		case 2:
			balanceWindowResized();
		break;
	}
}

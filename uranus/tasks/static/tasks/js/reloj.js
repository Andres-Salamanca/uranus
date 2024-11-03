function loadGauge() {
    var canvas = document.querySelector('canvas');
    var ctx = canvas.getContext('2d');
    var width = canvas.width = window.innerWidth;
    var height = canvas.height = window.innerHeight;

    /* constVar */

    var constVarOutput = $(".gauge-constVar")[0];
    var constVarRangeslider = $(".slider-range-constVar")[0];

    gaugePS = new RadialGauge({
        renderTo: constVarOutput,
        width: 175,
        height: 175,
        units: $(constVarOutput).attr('name'),
        title: false,
        minValue: 0,
        maxValue: 100,
        exactTicks: true,
        majorTicks: [
            '0',
            '10',
            '20',
            '30',
            '40',
            '50',
            '60',
            '70',
            '80',
            '90',
            '100'
        ],
        minorTicks: 1,
        ticksAngle: 270,
        startAngle: 45,
        strokeTicks: true,
        highlights: false,
        valueInt: 3,
        valueDec: 0,
        colorPlate: "#fff",
        colorMajorTicks: "#000",
        colorMinorTicks: "#686868",
        colorTitle: "#000",
        colorUnits: "#000",
        colorNumbers: "#000",
        valueBox: true,
        colorValueText: "#000",
        colorNeedle: "rgba(240, 128, 128, 1)",
        colorNeedleEnd: "rgba(255, 160, 122, .9)",
        borderShadowWidth: 0,
        borders: true,
        borderInnerWidth: 3,
        borderMiddleWidth: 3,
        borderOuterWidth: 3,
        needleType: "arrow",
        needleWidth: 4,
        needleCircleSize: 10,
        needleCircleOuter: true,
        needleCircleInner: true,
        fontNumbersSize: 20,
        fontTitleSize: 19,
        fontUnitsSize: 19,
        fontValueSize: 28,
        animationDuration: 0,
        animationSpeed: 32
    });

    gaugePS.draw();
    gaugePS.value = '0';

    constVarRangeslider.oninput = function () {
        $('#tooltiptext1').hide();
        constVar = this.value;
        gaugePS.update({valueText: this.value});
        gaugePS.update({value: this.value});
    };

    /* dpdVar */

    var dpdVarOutput = $(".gauge-dpdVar")[0];
    var dpdVarRangeslider = $(".slider-range-dpdVar")[0];

    dpdVarGauge = new RadialGauge({
        renderTo: dpdVarOutput,
        width: 175,
        height: 175,
        units: $(dpdVarOutput).attr('name'),
        title: false,
        minValue: 0,
        maxValue: 100,
        exactTicks: true,
        majorTicks: [
            '0',
            '10',
            '20',
            '30',
            '40',
            '50',
            '60',
            '70',
            '80',
            '90',
            '100'
        ],
        minorTicks: 1,
        ticksAngle: 270,
        startAngle: 45,
        strokeTicks: true,
        highlights: false,
        valueInt: 3,
        valueDec: 0,
        colorPlate: "#fff",
        colorMajorTicks: "#000",
        colorMinorTicks: "#686868",
        colorTitle: "#000",
        colorUnits: "#000",
        colorNumbers: "#000",
        valueBox: true,
        colorValueText: "#000",
        colorNeedle: "rgba(240, 128, 128, 1)",
        colorNeedleEnd: "rgba(255, 160, 122, .9)",
        borderShadowWidth: 0,
        borders: true,
        borderInnerWidth: 3,
        borderMiddleWidth: 3,
        borderOuterWidth: 3,
        needleType: "arrow",
        needleWidth: 4,
        needleCircleSize: 10,
        needleCircleOuter: true,
        needleCircleInner: true,
        fontNumbersSize: 20,
        fontTitleSize: 19,
        fontUnitsSize: 19,
        fontValueSize: 28,
        animationDuration: 0,
        animationSpeed: 32
    });

    dpdVarGauge.draw();
    dpdVarGauge.value = '0';

    dpdVarRangeslider.oninput = function () {
        $('#tooltiptext2').hide();
        topVar = this.value;
        dpdVarGauge.update({valueText: this.value});
        dpdVarGauge.update({value: this.value});
    };
}

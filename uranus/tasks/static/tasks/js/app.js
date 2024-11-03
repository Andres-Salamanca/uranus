$(document).ready(function() {

	$( function() {
    	var datepicker = $("#datepicker");
    	datepicker.datepicker({
    		changeYear: true,
    		yearRange: "-100:+0",
    		dateFormat: 'dd/mm/yy',
    		monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
    		dayNamesMin: ['Do','Lu','Ma','Mi','Ju','Vi','Sá'],
    		onSelect: function(date) {
            	$("#gender").show();
        	}
    	}); 
  	});

  	if (firsht) {
  		$("#m3").show();
  	}

  	$(document).on("click", ".info-ok-action", function (e) {
		$("#m3").hide();
    });

	$(document).on("keyup", ".input-action", function (e) {
		$("#datepicker").show();
    });

    $(document).on("change", ".gender-action", function (e) {
		$("#level").show();
    });

	$(document).on("change", ".level-action", function (e) {
		if (this.value === 'U') {
			$("#global").attr('university', 'true');
			$('#global').attr('primary', 'false');
			$('#global').attr('secundary', 'false');
		}
		if (this.value === 'P') {
			$("#global").attr('prymary', 'true');
			$('#global').attr('secundary', 'false');
			$('#global').attr('university', 'false');
		}
		if (this.value === 'S') {
			$("#global").attr('secundary', 'true');
			$('#global').attr('primary', 'false');
			$('#global').attr('university', 'false');
		}
		requestAjax('get-departaments',{}, loadDepartaments, msgError);		
    });
   
    $(document).on("keyup", ".facult-action", function (e) {
		$("#semester").show();
    });

    $(document).on("change", ".grade-primary-action", function (e) {
		$('.btn-register').addClass("move-right");
		$("#reg").removeAttr('disabled');
		$("#reg").removeClass('no-active');
    });

    $(document).on("change", ".grade-secundary-action", function (e) {
    	$('.btn-register').addClass("move-right");
    	$("#reg").removeAttr('disabled');
		$("#reg").removeClass('no-active');
    });

    $(document).on("change", ".semester-action", function (e) {
		$("#reg").removeAttr('disabled');
		$("#reg").removeClass('no-active');
    });

	$(document).on("click", ".start-action", function (e) {
		window.location="/registro"; 
	});
	
	$(document).on("click", ".show-pimage-action", function (e) {
		$('#m1').show();
	});
	
	$(document).on("click", ".intro-msg-box", function (e) {
		var msg = $(this).attr('msg'); 
		$('#' + msg).show();
	});

	$(document).on("click", ".hide-modal-action", function (e) {
		$('.m-modal').hide();
	});
	
	$(document).on("click", ".attempt-action", function (e) {
		if (!$('.attempt-action').hasClass('no-active')) {
			if (topVar === 0 && constVar === 0) {
				$("#msg-modal").empty();
				$("#msg-modal").dialog('option', 'title', 'Información');
				$("#msg-modal").append('Debes asignar un valor a las varibles.');
				$("#msg-modal").dialog("open");
			} else {
				x = 0;
				runFlag = true;
				$('.indicartor-box').show(); 
			}
		}
		$(".attempt-action").addClass('no-active');
  });

	$(document).on("click", ".recorder-action", function (e) {
		if (!$('.recorder-action').hasClass('no-active')) {
			startRecording();
			$('.des-box').append('<p>Grabando...</p>');
			$('.recorder-action').hide();
			$(".continue-action").show();
		}
	});

	$(document).on("click", ".continue-action", function (e) {
		if (!$('.continue-action').hasClass('no-active')) {
			$(".continue-action").addClass('no-active');
			$(".continue-action").html('Procesando...');
			stopRecording();
			$(".continue-action").removeClass('no-active');
			$(".continue-action").html('Terminar');
			switch(secuency) {
				case 0:
					$(".des-box").empty();				
					$(".des-box").append('¿Por qué lo harías de ésta maneta? Da clic en grabar y responde la pregunta en voz alta y luego da clic en terminar por favor.');		
					$(".continue-action").hide();
					$('.recorder-action').show();
					break;
				case 1:
					$(".des-box").empty();				
					$(".des-box").append('¿Qué te hace pensar que es la mejor solución? Da clic en grabar y responde la pregunta en voz alta y luego da clic en terminar por favor.');
					$(".continue-action").hide();
					$('.recorder-action').show();
					break;
				default:
					$(".continue-action").addClass('no-active');
					$(".continue-action").html('Procesando...');
			}	
			secuency++;			
		}
	});

	$(".register-action").submit(function(e) {
		if(validateForm()) {
			var dta = $(this).serialize();
			$.ajax({
				type: "POST",
				url: "/register-action",
				dataType: "json",
				data: dta,
				success: function(response) {
					if(response.state) {
						window.location="/introduccion?id=" + response.session_id; 						
					} else {
						$(".alert").empty();
						$(".alert").append('<p>' + response.msg + '</p>');			
					}
				},
				error: function() {
					$(".alert").empty();
					$(".alert").append('<p>Datos incorrectos, verifica la fecha o los otros campos.</p>');
				}
			});	    
		} else {
			$(".alert").empty();
			$(".alert").append('<p>Debes llenar todos los campos</p>');
		}
		e.preventDefault();
	});

});

var showText = function (target, message, index, interval) {   
	if (index < message.length) {
	$(target).append(message[index++]);
	if ((index % 8) == 0) {	
		$("#signal").css("border-color", "#fff");
	} else {
		$("#signal").css("border-color", "#f06060");
	}
	setTimeout(function () { showText(target, message, index, interval); }, interval);
	} else {
		setTimeout(function () {
			$("#signal").remove();
			$("#message-modal").remove();
		}, 1000);
	}
}

function validateForm() {
	alias = $('#alias').val();
	datepicker = $('#datepicker').val();
	gender = $('#gender').val();	
	level = $('#level').val();
	departament = $('#departament').val();
	city = $('#city').val();
	institute = $('#institute').val();
	if (alias != '' && datepicker != '' && gender != '' && 
		level != '' && departament != '' && city != '' && institute) {
		
		if ($('#global').attr('university') === 'true') {
			facult = $('#facult').val();
			semester = $('#semester').val();
			if (facult === '' || semester === '') {
				return false;
			}
		}
		if ($('#global').attr('prymary') === 'true') {
			gradePrimary = $('.grade-primary-action').val();
			if (gradePrimary === '') {
				return false;
			}
		}
		if ($('global').attr('secundary') === 'true') {
			gradeSecundary = $('.grade-secundary-action').val();
			if (gradeSecundary === '') {
				return false;
			}
		}
	} else {
		return false;
	}
	return true;
}

function continueAction() {
	$(this).dialog("close");
}

function loadSchools(data) {
	var html = '<option value="" disabled selected>Selecciona tu colegio (Ej.: Liceo Cervantes)</option>';
	var institute = $("#institute");
	institute.empty();
	if (data.length > 0) {
		for (var i = 0; i < data.length; i++) {
			html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';			
		}
		institute.append(html);		
		$( "#institute" ).combobox();
	    $( "#toggle" ).click(function() {
	      $( "#institute" ).toggle();
	    });
	    $('#institute-box > .ui-combobox > .ui-combobox-input').attr('action','institute-action')
		$('.ui-combobox-input').show();
	} else {
		$(".alert").empty();
		$(".alert").append('<p>No tenemos colegios registrados en este municipio.</p>');
	}
}

function loadUniversities(data) {
	var html = '<option value="" disabled selected>Selecciona tu universidad (Ej.: Pontificia Universidad Javeriana)</option>';
	var institute = $("#institute");
	institute.empty();
	if (data.length > 0) {
		for (var i = 0; i < data.length; i++) {
			html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';			
		}
		institute.append(html);		
		$( "#institute" ).combobox();
	    $( "#toggle" ).click(function() {
	      $( "#institute" ).toggle();
	    });
	    $('#institute-box > .ui-combobox > .ui-combobox-input').attr('action','institute-action')
		$('.ui-combobox-input').show();
	} else {
		$(".alert").empty();
		$(".alert").append('<p>No tenemos universidades registradas en este municipio.</p>');
	}	
}

function loadCities(data) {
	var html = '<option value="" disabled selected>Selecciona tu municipio (Ej.: Duitama)</option>';
	var city = $("#city");
	city.empty();
	for (var i = 0; i < data.length; i++) {
		html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';			
	}
	city.append(html);		
	$( "#city" ).combobox();
    $( "#toggle" ).click(function() {
      $( "#city" ).toggle();
    });
    $('#city-box > .ui-combobox > .ui-combobox-input').attr('action','city-action')
	$('.ui-combobox-input').show();
}

function loadDepartaments(data) {
	var departament = $("#departament");	
	var html = '<option value="" disabled selected>Selecciona el departamento de tu institución (Ej.: Boyacá)</option>';	
	departament.empty();
	for (var i = 0; i < data.length; i++) {
		html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';			
	}
	departament.append(html);
	$( "#departament" ).combobox();
    $( "#toggle" ).click(function() {
      $( "#departament" ).toggle();
    });
    $('#departament-box > .ui-combobox > .ui-combobox-input').attr('action','departament-action')
	$('.ui-combobox-input').show();
}

function departamentAction(value) {
	requestAjax('get-cities',{dptID: parseInt(value)}, loadCities, msgError);
}    

function cityAction(value) {
	if ($("#global").attr('university') === 'true') {
		requestAjax('get-universities',{ctyID: parseInt(value)}, loadUniversities, msgError);
	} else {
		requestAjax('get-schools',{ctyID: parseInt(value)}, loadSchools, msgError);
	}
}

function instituteAction(value) {
	if ($("#global").attr('university') === 'true') {
			$("#facult").show();
	} else {
		if ($("#global").attr('prymary') === 'true') {
			$("#grade-primary").show();
		} else {
			$("#grade-secundary").show();
		}			
	}
}

function msgError() {
	alert('Try agein ;)');
}

function requestAjax(action, dto, doneFunction, failFuntion) {
	dto['' + $('#csrftoken > input').attr('name')] = $('#csrftoken > input').val();
    $.ajax({
        url: "/" + action,
        type: 'POST',
        data: dto,
        contentType: 'application/x-www-form-urlencoded',
    }).done(function(response) {               
        if (doneFunction != null) {
            doneFunction(JSON.parse(response));
        }            
    }).fail(function(jqXHR, textStatus) {
        if (failFuntion != null) {
            failFuntion(textStatus);
        } 
    }); 
}

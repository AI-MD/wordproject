	$(document).keydown(function(e) {
	    key = (e) ? e.keyCode : event.keyCode;
	     
	    var t = document.activeElement;
	     
	    if (key == 8 || key == 116 || key == 17 || key == 82) {
	        if (key == 8) {
	            if (t.tagName != "INPUT") {
	                if (e) {
	                    e.preventDefault();
	                } else {
	                    event.keyCode = 0;
	                    event.returnValue = false;
	                }
	            }
	        } else {
	            if (e) {
	                e.preventDefault();
	            } else {
	                event.keyCode = 0;
	                event.returnValue = false;
	            }
	        }
	    }
	});

	
	function onlyAlphabet(obj) {
		  $(obj).keyup(function(){
			$(this).val($(this).val().replace(/[^a-z]/gi,""));
		});
  	}

function removeCliente(url, id){ 
		 
				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");
		
			               $.ajax({
			                     type: "POST",	                   
			                     url: url,
			                     beforeSend: function(xhr) {	                        
			                         xhr.setRequestHeader(header, token);
			                     },
			                     success: function () {
			                    	 
			                    	 var cliente = $('#'+id);
			                    	 cliente.css({"opacity": "0", "transition": "0.5s"});
			                    	 
			                    	 setTimeout(function() {
			                    		 cliente.remove();
									},500);
			                     }
			               });
			};
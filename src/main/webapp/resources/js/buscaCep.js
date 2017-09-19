/*!
 * buscador de dados de endereço pelo cep 
 * consumindo o ws do viacep.com.br
 */

	var inputsCep = $('#logradouro, #bairro, #localidade, #uf');
	var validaCep = /^[0-9]{8}$/;
	
	function limpa_formulario_cep(alerta){
		if(alerta !== undefined){
			alert(alerta);
		}
		inputsCep.val("");
	};
	
	function get(url){
		$.get(url, function(data){
			if(!("erro" in data)){
				if(Object.prototype.toString.call(data) === "[object Array]"){
					var data = data[0];				
				}
				$.each(data, function(nome, info){
					$('#'+nome).val(info);
				});
			}else {
				limpa_formulario_cep("Cep não encontrado.");
			}
		});
	};
	
	$("#cep").on("blur", function(e){
		var cep = $("#cep").val().replace(/\D/g, '');
		
		if(cep !== "" && validaCep.test(cep)){
			inputsCep.val('...');
		    get('https://viacep.com.br/ws/' + cep + '/json/');
		} else{
			limpa_formulario_cep(cep == "" ? undefined : "Formato de Cep inválido");
		}
	});
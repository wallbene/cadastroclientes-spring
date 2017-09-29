

var filtro = $("#filtra-clientes");
filtro.on("input", function(){
	console.log("digitando");
	var clientes = $(".cliente");
	
	for(var i = 0; i< clientes.length; i++){
		var clienteTr = clientes[i];
		var nomeTd = clienteTr.querySelector(".info-nome");
		var nome = nomeTd.textContent;
		
	var regExp	= new RegExp(this.value, "i");
	
	if(!regExp.test(nome)){
		clienteTr.classList.add("collapse");
	}else{
		clienteTr.classList.remove("collapse");
	}
	}
	
});
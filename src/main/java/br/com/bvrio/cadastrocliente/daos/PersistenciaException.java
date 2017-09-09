package br.com.bvrio.cadastrocliente.daos;

import br.com.bvrio.cadastrocliente.errors.ErroDePersistencia;

public class PersistenciaException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private ErroDePersistencia erro;

	public PersistenciaException(ErroDePersistencia erro) {
		super(erro.toString());
		this.erro = erro;
	}

	public ErroDePersistencia getErro() {
		return erro;
	}

}

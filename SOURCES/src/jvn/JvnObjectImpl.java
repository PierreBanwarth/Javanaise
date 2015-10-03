package jvn;

import java.io.Serializable;

public class JvnObjectImpl implements JvnObject{
	

	//private State state = State.W;
	// ATTENTION : l'état (state) n'est pas forcément l'état du lock. Ca peut aussi être la phrase à écrire sur le chat.
	// Il s'agit donc d'un type générique sérialisable qui doit être idéalement défini par la suite en fonction du type d'objet instancié.
	private Serializable state = null;
	
	private int id;
	
	public JvnObjectImpl(int id){
		super();
		this.id = id;
		this.state = State.W;
	}

	public  void  jvnLockRead() throws JvnException {
		// TODO Auto-generated method stub
		JvnServerImpl server;
		switch((State)state){
		case NL:
			server = JvnServerImpl.jvnGetServer();
			server.jvnLockRead(this.jvnGetObjectId());
			this.state = State.R;
			break;
		case R:
		case RC:
			server = JvnServerImpl.jvnGetServer();
			server.jvnLockRead(this.jvnGetObjectId());
			this.state = State.R;
			break;
		case WC:
		case RWC:
			this.state = State.RWC;
			break;
		case W:
			throw new JvnException("[jvnLockRead()] State error in JvnObjectImpl");
		}
		
	}

	public void jvnLockWrite() throws JvnException {
		// TODO Auto-generated method stub
		JvnServerImpl server;
		switch((State)state){
		case NL:
		case RC:
			server = JvnServerImpl.jvnGetServer();
			server.jvnLockWrite(this.jvnGetObjectId());
			this.state = State.W;
			break;
		case W:
		case WC:
		case RWC:
			this.state = State.W;
			break;
		case R:
			throw new JvnException("[jvnLockWrite()] State error in JvnObjectImpl");
		}
		
	}

	public void jvnUnLock() throws JvnException {
		// TODO Auto-generated method stub
		JvnServerImpl server;
		switch((State)state){
			case R:
			case RC:
			case RWC:
				server = JvnServerImpl.jvnGetServer();
				server.jvnLockRead(this.jvnGetObjectId());
				this.state = State.RC;
				break;
			case W:
			case WC:
				server = JvnServerImpl.jvnGetServer();
				server.jvnLockRead(this.jvnGetObjectId());
				this.state = State.WC;
				break;
			case NL:
				break;
		}
		
	}

	public int jvnGetObjectId() throws JvnException {
		// TODO Auto-generated method stub
		return this.id;
	}

	public Serializable jvnGetObjectState() throws JvnException {
		// TODO Auto-generated method stub
		return this.state;
	}

	public void jvnInvalidateReader() throws JvnException {
		// TODO Auto-generated method stub
		if(this.state == State.RC || this.state == State.R){
			this.state = State.NL;
		} 
		
	}

	public Serializable jvnInvalidateWriter() throws JvnException {
		// TODO Auto-generated method stub
		if(this.state == State.W){
			this.state = State.NL;
		} 
		return this.id;
	}

	public Serializable jvnInvalidateWriterForReader() throws JvnException {
		// TODO Auto-generated method stub
		if(this.state == State.RWC){
			this.state = State.R;
		} 
		return this.id;
	}
}
package jvn;

import java.io.Serializable;

public class JvnObjectImpl implements JvnObject{
	

	//private State state = State.W;
	// ATTENTION : l'état (state) n'est pas forcément l'état du lock. Ca peut aussi être la phrase à écrire sur le chat.
	// Il s'agit donc d'un type générique sérialisable qui doit être idéalement défini par la suite en fonction du type d'objet instancié.
	private State state = State.W;
	private Serializable objet = null;
	
	private int id;
	
	public JvnObjectImpl(int id){
		super();
		this.id = id;
		state = State.W;
	}

	public  void  jvnLockRead() throws JvnException {
		// TODO Auto-generated method stub
		System.out.println("lockR :"+state);
		switch(state){
		case NL:
			objet = JvnServerImpl.jvnGetServer().jvnLockRead(this.id);
			state = State.R;
			break;
		case R:
		case RC:
			JvnServerImpl.jvnGetServer().jvnLockRead(this.jvnGetObjectId());
			state = State.R;
			break;
		case WC:
		case RWC:
			state = State.RWC;
			break;
		case W:
			throw new JvnException("[jvnLockRead()] State error in JvnObjectImpl");
		}
		
	}

	public void jvnLockWrite() throws JvnException {
		// TODO Auto-generated method stub
		System.out.println("lockW :"+state);
		switch(state){
		case NL:
			objet = JvnServerImpl.jvnGetServer().jvnLockRead(this.jvnGetObjectId());
			break;
		case RC:
			JvnServerImpl.jvnGetServer().jvnLockWrite(this.jvnGetObjectId());
			state = State.W;
			break;
		case W:
		case WC:
		case RWC:
			state = State.W;
			break;
		case R:
			throw new JvnException("[jvnLockWrite()] State error in JvnObjectImpl");
		}
		
	}

	public void jvnUnLock() throws JvnException {
		// TODO Auto-generated method stub
		System.out.println("unlock :"+state);
		switch(state){
			case R:
			case RC:
			case RWC:
				JvnServerImpl.jvnGetServer().jvnLockRead(this.jvnGetObjectId());
				state = State.RC;
				break;
			case W:
			case WC:
				JvnServerImpl.jvnGetServer().jvnLockRead(this.jvnGetObjectId());
				state = State.WC;
				break;
			case NL:
				objet = JvnServerImpl.jvnGetServer().jvnLockRead(this.jvnGetObjectId());
				break;
		}
		
	}

	public int jvnGetObjectId() throws JvnException {
		// TODO Auto-generated method stub
		return id;
	}

	public Serializable jvnGetObjectState() throws JvnException {
		// TODO Auto-generated method stub
		return objet;
	}

	public void jvnInvalidateReader() throws JvnException {
		// TODO Auto-generated method stub
		if(state == State.RC || state == State.R){
			state = State.NL;
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
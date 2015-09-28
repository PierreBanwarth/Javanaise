package jvn;

import java.io.Serializable;

public class JvnObjectImpl implements JvnObject {
	
	private State state = State.WLT;
	private int id;
	private Serializable serial;
	
	public JvnObjectImpl(int id){
		super();
		this.id = id;
		
	}
	synchronized public void jvnLockRead() throws JvnException {
		JvnServerImpl server;
		//contact du coordinateur
		switch(this.state){
		// cas ou l'on a rien
		case NL:
			server = JvnServerImpl.jvnGetServer();
			server.jvnLockRead(this.jvnGetObjectId());
			this.state = State.RLT;
			break;
		case RLC:
			server = JvnServerImpl.jvnGetServer();
			server.jvnLockRead(this.jvnGetObjectId());
			this.state = State.RLT;
			break;
		case WLC:
			this.state = State.RLT_WLC;
			break;
		default:
			throw new JvnException("[jvnLockRead()] State error");
		}
	}

	synchronized public void jvnLockWrite() throws JvnException {
		JvnServerImpl server;
		switch(this.state){
		case NL:
			server = JvnServerImpl.jvnGetServer();
			server.jvnLockWrite(this.jvnGetObjectId());
			this.state = State.WLT;
			break;
		case RLC:
			server = JvnServerImpl.jvnGetServer();
			server.jvnLockWrite(this.jvnGetObjectId());
			this.state = State.WLT;
			break;
		case WLC:
			this.state = State.WLT;
			break;
		
		default:
			throw new JvnException("[jvnLocWrite()] State error");
		}
	}

	public void jvnUnLock() throws JvnException {
		JvnServerImpl server;
		switch(this.state){
		case RLT:
			server = JvnServerImpl.jvnGetServer();
			server.jvnLockRead(this.jvnGetObjectId());
			this.state = State.WLT;
			break;
		case WLT:
			server = JvnServerImpl.jvnGetServer();
			server.jvnLockRead(this.jvnGetObjectId());
			this.state = State.WLC;
			break;
		case WLC:
			this.state = State.WLT;
			break;
		
		default:
			throw new JvnException("[jvnUnlock()] State error");
		}

	}

	public int jvnGetObjectId() {
		return 0;
	}

	public Serializable jvnGetObjectState() {
		return null;
	}

	public void jvnInvalidateReader() {
		
	}

	public Serializable jvnInvalidateWriter() {
		return null;
	}

	public Serializable jvnInvalidateWriterForReader() {
		return null;
	}
	public void setObject(Serializable o) {
		// TODO Auto-generated method stub
		
	}

}

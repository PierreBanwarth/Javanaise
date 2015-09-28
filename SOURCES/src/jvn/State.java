package jvn;

public enum State {
	
	NL, RLC, WLC, RLT, WLT, RLT_WLC;
	// no lock
	// Read lock cached 
	// write lock cached 
	// Read lock taken
	// Write lock taken
	// read lock taken write lock cached
	
	
}
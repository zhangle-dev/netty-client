package com.zl;

public interface ClientCallback<T> {

	void callback(T packet);
}

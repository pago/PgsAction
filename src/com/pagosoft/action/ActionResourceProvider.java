package com.pagosoft.action;

/**
 * <p>Implementations of this interface will be used by
 * {@link AbstractSystemAction} to transform
 * the id into a basic setup of the action.</p>
 *
 * @author Patrick Gotthardt
 */
public interface ActionResourceProvider {
	/**
	 * <p>Return a String as specified in {@link AbstractSystemAction}:</p>
	 * <pre><code>Name with &mnemonic@keystroke</code></pre>
	 * <p>Where both the mnemonic identifier and the keystroke are optional.</p>
	 * @param action
	 * @param key
	 * @return String in specific format.
	 */
	public String getString(Object action, String key);

	/**
	 * <p>This is currently only used to create a keystroke-compatible string.</p>
	 * @param action
	 * @param id
	 * @return Some other configuration
	 */
	public Object getConfig(Object action, String id);
}

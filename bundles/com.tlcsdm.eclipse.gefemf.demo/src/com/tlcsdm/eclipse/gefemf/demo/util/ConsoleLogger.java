/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.util;

import java.io.IOException;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import com.tlcsdm.eclipse.gefemf.demo.Activator;

/**
 * Utility class for logging messages to the Eclipse platform log and console.
 * Provides methods for logging info, warning, and error messages.
 */
public final class ConsoleLogger {

	/** Console name for LVGL Code Generator output */
	private static final String CONSOLE_NAME = "LVGL Code Generator";
	
	/** Prefix for log messages */
	private static final String LOG_PREFIX = "[LVGL Code Generator] ";

	private ConsoleLogger() {
		// Utility class, prevent instantiation
	}

	/**
	 * Log an info message to the Eclipse platform log.
	 * 
	 * @param message the message to log
	 */
	public static void logInfo(String message) {
		ILog log = Activator.getDefault().getLog();
		log.log(new Status(IStatus.INFO, Activator.PLUGIN_ID, message));
	}

	/**
	 * Log a warning message to the Eclipse platform log.
	 * 
	 * @param message the message to log
	 */
	public static void logWarning(String message) {
		ILog log = Activator.getDefault().getLog();
		log.log(new Status(IStatus.WARNING, Activator.PLUGIN_ID, message));
	}

	/**
	 * Log an error message to the Eclipse platform log.
	 * 
	 * @param message the message to log
	 */
	public static void logError(String message) {
		ILog log = Activator.getDefault().getLog();
		log.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, message));
	}

	/**
	 * Log an error message with an exception to the Eclipse platform log.
	 * 
	 * @param message the message to log
	 * @param e the exception to log
	 */
	public static void logError(String message, Throwable e) {
		ILog log = Activator.getDefault().getLog();
		log.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, message, e));
	}

	/**
	 * Write a message to the Eclipse console view.
	 * Creates or reuses the LVGL Code Generator console.
	 * 
	 * @param message the message to write
	 */
	public static void writeToConsole(String message) {
		MessageConsole console = findOrCreateConsole();
		try (MessageConsoleStream stream = console.newMessageStream()) {
			stream.println(LOG_PREFIX + message);
		} catch (IOException e) {
			// Fallback to log if console write fails
			logError("Failed to write to console", e);
		}
	}

	/**
	 * Find an existing LVGL Code Generator console or create a new one.
	 * Also activates and brings the console to front.
	 * 
	 * @return the MessageConsole instance
	 */
	private static MessageConsole findOrCreateConsole() {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager consoleManager = plugin.getConsoleManager();
		IConsole[] existing = consoleManager.getConsoles();
		
		for (IConsole console : existing) {
			if (CONSOLE_NAME.equals(console.getName())) {
				MessageConsole msgConsole = (MessageConsole) console;
				// Show and activate the console
				consoleManager.showConsoleView(msgConsole);
				return msgConsole;
			}
		}
		
		// No console found, create a new one
		MessageConsole newConsole = new MessageConsole(CONSOLE_NAME, null);
		consoleManager.addConsoles(new IConsole[] { newConsole });
		// Show and activate the console
		consoleManager.showConsoleView(newConsole);
		return newConsole;
	}
}

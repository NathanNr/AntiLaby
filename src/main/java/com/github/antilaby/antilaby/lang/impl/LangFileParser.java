package com.github.antilaby.antilaby.lang.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class LangFileParser {
	
	private static final Pattern VERSION_PATTERN = Pattern.compile("(^|.*\\s)version:\\s?\\d*\\.?\\d*\\.?\\d*$");
	private static final Pattern REPLACER = Pattern.compile("^.*version:\\s?");
	
	public static LanguageVersion getVersion(String headerFrom) {
		if (VERSION_PATTERN.matcher(headerFrom).matches())
			return LanguageVersion.getOrCreate(REPLACER.matcher(headerFrom).replaceAll(""));
		return LanguageVersion.UNDEFINED;
		
	}
	
	public static Map<String, String> parse(File f) {
		try {
			return parse(new FileInputStream(f), f.getName());
		} catch (final FileNotFoundException e) {
			return null;
		}
	}
	
	public static Map<String, String> parse(InputStream resource) {
		return parse(resource, "unknown");
	}
	
	public static Map<String, String> parse(InputStream resource, String nameToUse) {
		LanguageManager.LOG.info("Trying to load language resource: " + nameToUse);
		final Map<String, String> result = new HashMap<>();
		if (resource == null) return result;
		final LanguageVersion vr = LanguageVersion.UNDEFINED;
		try {
			final BufferedReader r = new BufferedReader(new InputStreamReader(resource, Charset.forName("UTF-8")));
			int line = 0;
			while (r.ready()) {
				line++;
				String ln = r.readLine();
				ln = ln.trim();
				if (ln.startsWith("#")) {
					if (line == 1 && ln.contains("version:")) ;
				} else if (!ln.contains("=")) LanguageManager.LOG
						.warn("Could not parse line in resource " + nameToUse + " line " + line + ": " + ln);
				else {
					final String[] s = ln.split("=", 2);
					result.put(s[0].trim().replace(" ", ""), s[1].trim());
					
				}
			}
			r.close();
		} catch (final Exception e) {
			LanguageManager.LOG.error("Could not parse lang resource " + nameToUse + ": " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	private final Locale l;
	
	public LangFileParser(Locale l) {
		this.l = l;
	}

	public Map<String, String> parse() {
		return parse(getClass().getClassLoader().getResourceAsStream(l.getName() + ".lang"), l.getName() + ".lang");
	}

}
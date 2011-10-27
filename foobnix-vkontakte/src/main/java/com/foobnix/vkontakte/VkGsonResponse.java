/* Copyright (c) 2011 Ivan Ivanenko <ivan.ivanenko@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE. */
package com.foobnix.vkontakte;

import java.util.ArrayList;
import java.util.List;

import com.foobnix.commons.log.LOG;
import com.foobnix.commons.string.StringUtils;
import com.foobnix.exception.VkErrorException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class VkGsonResponse {

	public static <T> List<T> toModels(String json, Class<T> clazz) throws VkErrorException {
		return toModels(json, clazz, 1);
	}

	public static <T> List<T> toModels(String json, Class<T> clazz, int startIndex)
	        throws VkErrorException {
		List<T> result = new ArrayList<T>();

		if (StringUtils.isEmpty(json) || json.trim().equalsIgnoreCase("{\"response\":{}}")) {
			LOG.d("Recive an empty json", json);
			return result;
		}
		if (json.trim().startsWith("{\"error\":")) {
			throw new VkErrorException(json);
		}

		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonArray array = parser.parse(json).getAsJsonObject().getAsJsonArray("response");
		


		for (int i = startIndex; i < array.size(); i++) {
			JsonElement next = array.get(i);
			T fromJson = gson.fromJson(next, clazz);
			result.add(fromJson);
		}
		return result;
	}

}

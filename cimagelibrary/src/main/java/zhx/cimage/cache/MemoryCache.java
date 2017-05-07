/*******************************************************************************
 * Copyright 2014 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package zhx.cimage.cache;

import android.graphics.Bitmap;

import java.util.Collection;

/**
 * Interface for memory cache
 */
public interface MemoryCache {
	/**
	 *缓存bitmap
	 * cache
	 */
	boolean put(String key, Bitmap value);

	/** 获取缓存的 bitmap */
	Bitmap get(String key);

	/** 移除缓存/Removes item by key */
	Bitmap remove(String key);

	/** 获取所有缓存/Returns all keys of cache */
	Collection<String> keys();

	/** 清除所有缓存/Remove all items from cache */
	void clear();
}

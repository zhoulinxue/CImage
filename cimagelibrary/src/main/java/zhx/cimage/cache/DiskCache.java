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


import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import zhx.cimage.utils.IoUtils;

/**
 * Interface for disk cache
 */
public interface DiskCache {
	/**
	 * 获取缓存文件夹/Returns root directory of disk cache
	 *
	 * @return Root directory of disk cache
	 */
	File getDirectory();

	/**
	 * 获取缓存文件/Returns file of cached image
	 *
	 * @param imageUri Original image URI
	 * @return File of cached image or <b>null</b> if image wasn't cached
	 */
	File get(String imageUri);

	/**
	 * 缓存文件流/Saves image stream in disk cache.
	 * @return <b>true</b> - if image was saved successfully; <b>false</b> - if image wasn't saved in disk cache.
	 * @throws java.io.IOException
	 */
	boolean save(String imageUri, InputStream imageStream, IoUtils.CopyListener listener) throws IOException;

	/**
	 * Saves image bitmap in disk cache.
	 *
	 * @param imageUri Original image URI
	 * @param bitmap   Image bitmap
	 * @return <b>true</b> - if bitmap was saved successfully; <b>false</b> - if bitmap wasn't saved in disk cache.
	 * @throws IOException
	 */
	boolean save(String imageUri, Bitmap bitmap) throws IOException;

	/**
	 * Removes image file associated with incoming URI
	 *
	 * @param imageUri Image URI
	 * @return <b>true</b> - if image file is deleted successfully; <b>false</b> - if image file doesn't exist for
	 * incoming URI or image file can't be deleted.
	 */
	boolean remove(String imageUri);

	/** Closes disk cache, releases resources. */
	void close();

	/** Clears disk cache. */
	void clear();
}

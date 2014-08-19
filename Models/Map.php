<?php
/**
 * @copyright 2010-2014 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
namespace Application\Models;

class Map extends ActiveRecord
{
	public static $extensions = array(
		'jpg'  => ['mime_type'=>'image/jpeg', 'media_type'=>'image'],
		'gif'  => ['mime_type'=>'image/gif' , 'media_type'=>'image'],
		'png'  => ['mime_type'=>'image/png' , 'media_type'=>'image'],
		'tiff' => ['mime_type'=>'image/tiff', 'media_type'=>'image']
	);

	/**
	 * @param string $directory Where to store the file
	 * @param array|string $file  Either an entry from $_FILES or a path to a file
	 * @param string $newName	The filename to use
	 */
	public static function saveFile($directory, $file, $newName)
	{
		// Handle passing in either a $_FILES array or just a path to a file
		$tempFile = is_array($file) ? $file['tmp_name'] : $file;
		if (!$tempFile) {
			throw new \Exception('media/uploadFailed');
		}

		# Find out the mime type for this file
		$filename = is_array($file) ? basename($file['name']) : basename($file);
		preg_match("/[^.]+$/",$filename,$matches);
		$extension = strtolower($matches[0]);

		// Make sure it's a known file type
		if (!array_key_exists(strtolower($extension),self::$extensions)) {
			throw new \Exception('unknownFileType');
		}

		// Clean out any previous version of the file
		foreach(glob("$directory/$newName.*") as $file) {
			unlink($file);
		}

		// Move the file where it's supposed to go
		if (!is_dir($directory)) {
			mkdir($directory,0777,true);
		}
		$newFile = "$directory/$newName.$extension";
		rename($tempFile,$newFile);
		chmod($newFile,0666);

		if (!is_file($newFile)) {
			throw new \Exception('media/uploadFailed');
		}
	}
}

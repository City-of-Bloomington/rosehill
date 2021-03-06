<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
class Cemetery
{
	private $id;
	private $name;
	private $googleMapURL;

	/**
	 * Populates the object with data
	 *
	 * Passing in an associative array of data will populate this object without
	 * hitting the database.
	 *
	 * Passing in a scalar will load the data from the database.
	 * This will load all fields in the table as properties of this class.
	 * You may want to replace this with, or add your own extra, custom loading
	 *
	 * @param int|array $id
	 */
	public function __construct($id=null)
	{
		if ($id) {
			if (is_array($id)) {
				$result = $id;
			}
			else {
				$zend_db = Database::getConnection();
				if (ctype_digit($id)) {
					$sql = 'select * from cemeteries where id=?';
				}
				else {
					$sql = 'select * from cemeteries where name=?';
				}
				$result = $zend_db->fetchRow($sql,array($id));
			}

			if ($result) {
				foreach ($result as $field=>$value) {
					if ($value) {
						$this->$field = $value;
					}
				}
			}
			else {
				throw new Exception('cemeteries/unknownCemetery');
			}
		}
		else {
			// This is where the code goes to generate a new, empty instance.
			// Set any default values for properties that need it here
		}
	}

	/**
	 * Throws an exception if anything's wrong
	 * @throws Exception $e
	 */
	public function validate()
	{
		// Check for required fields here.  Throw an exception if anything is missing.
		if (!$this->name) {
			throw new Exception('missingName');
		}
	}

	/**
	 * Saves this record back to the database
	 */
	public function save()
	{
		$this->validate();

		$data = array();
		$data['name'] = $this->name;
		$data['googleMapURL'] = $this->googleMapURL ? $this->googleMapURL : null;

		if ($this->id) {
			$this->update($data);
		}
		else {
			$this->insert($data);
		}
	}

	private function update($data)
	{
		$zend_db = Database::getConnection();
		$zend_db->update('cemeteries',$data,"id='{$this->id}'");
	}

	private function insert($data)
	{
		$zend_db = Database::getConnection();
		$zend_db->insert('cemeteries',$data);
		$this->id = $zend_db->lastInsertId('cemeteries','id');
	}

	//----------------------------------------------------------------
	// Generic Getters
	//----------------------------------------------------------------

	/**
	 * @return int
	 */
	public function getId()
	{
		return $this->id;
	}

	/**
	 * @return string
	 */
	public function getName()
	{
		return $this->name;
	}

	/**
	 * @return string
	 */
	public function getGoogleMapURL()
	{
		return $this->googleMapURL;
	}
	//----------------------------------------------------------------
	// Generic Setters
	//----------------------------------------------------------------

	/**
	 * @param string $string
	 */
	public function setName($string)
	{
		$this->name = trim($string);
	}

	/**
	 * @param string $string
	 */
	public function setGoogleMapURL($string)
	{
		$this->googleMapURL = trim($string);
	}
	//----------------------------------------------------------------
	// Custom Functions
	// We recommend adding all your custom code down here at the bottom
	//----------------------------------------------------------------
	public function __toString()
	{
		return $this->name;
	}

	/**
	 * Returns all the available sections for this cemetery
	 *
	 * @return array
	 */
	public function getSections()
	{
		return new SectionList(array('cemetery_id'=>$this->id));
	}

	/**
	 * @return URL
	 */
	public function getURL()
	{
		return new URL(BASE_URL.'/cemeteries/viewCemetery.php?cemetery_id='.$this->id);
	}

	private function getMapDirectory()
	{
		return 'images/cemeteries/'.$this->id;
	}

	/**
	 * Returns the URL to the map image for this cemetery
	 *
	 * Available map types are:
	 * 		full, thumb - for the main map
	 *
	 * @return string
	 */
	public function getMap($type="full")
	{
		$imageDir = "images/cemeteries/{$this->id}";
		$filename = $type=='full' ? 'map' : 'map_thumb';

		$glob = glob(APPLICATION_HOME."/html/$imageDir/$filename.*");
		if (count($glob)) {
			$filename = basename($glob[0]);
			return BASE_URL."/$imageDir/$filename";
		}
	}

	/**
	 * @param array|string $file  Either an entry from $_FILES or a path to a file
	 */
	public function saveMap($file,$type)
	{
		$imageDir = $this->getMapDirectory();
		$name = $type=='full' ? 'map' : 'map_thumb';

		$directory = APPLICATION_HOME."/html/$imageDir";

		Map::saveFile($directory,$file,$name);
	}
}

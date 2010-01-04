<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
class Section
{
	private $id;
	private $cemetery_id;
	private $code;
	private $name;

	private $cemetery;

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
				$sql = 'select * from sections where id=?';
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
				throw new Exception('sections/unknownSection');
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
		if (!$this->cemetery_id || !$this->code) {
			throw new Excepction('missingRequiredFields');
		}
	}

	/**
	 * Saves this record back to the database
	 */
	public function save()
	{
		$this->validate();

		$data = array();
		$data['cemetery_id'] = $this->cemetery_id;
		$data['code'] = $this->code;
		$data['name'] = $this->name ? $this->name : null;

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
		$zend_db->update('sections',$data,"id='{$this->id}'");
	}

	private function insert($data)
	{
		$zend_db = Database::getConnection();
		$zend_db->insert('sections',$data);
		$this->id = $zend_db->lastInsertId('sections','id');
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
	 * @return int
	 */
	public function getCemetery_id()
	{
		return $this->cemetery_id;
	}

	/**
	 * @return string
	 */
	public function getCode()
	{
		return $this->code;
	}

	/**
	 * @return string
	 */
	public function getName()
	{
		return $this->name;
	}

	/**
	 * @return Cemetery
	 */
	public function getCemetery()
	{
		if ($this->cemetery_id) {
			if (!$this->cemetery) {
				$this->cemetery = new Cemetery($this->cemetery_id);
			}
			return $this->cemetery;
		}
		return null;
	}

	//----------------------------------------------------------------
	// Generic Setters
	//----------------------------------------------------------------

	/**
	 * @param int $int
	 */
	public function setCemetery_id($int)
	{
		$this->cemetery = new Cemetery($int);
		$this->cemetery_id = $int;
	}

	/**
	 * @param string $string
	 */
	public function setCode($string)
	{
		$this->code = trim($string);
	}

	/**
	 * @param string $string
	 */
	public function setName($string)
	{
		$this->name = trim($string);
	}

	/**
	 * @param Cemetery $cemetery
	 */
	public function setCemetery($cemetery)
	{
		$this->cemetery_id = $cemetery->getId();
		$this->cemetery = $cemetery;
	}


	//----------------------------------------------------------------
	// Custom Functions
	// We recommend adding all your custom code down here at the bottom
	//----------------------------------------------------------------
	public function __toString()
	{
		return $this->name ? $this->name : $this->code;
	}

	private function getMapDirectory()
	{
		return 'images/cemeteries/'.$this->cemetery_id;
	}

	/**
	 * Returns the URL to the map image
	 *
	 * @param string $type	Either 'highlight' or 'zoom'
	 * @return string
	 */
	public function getMap($type='highlight')
	{
		$imageDir = $this->getMapDirectory();
		$type = $type=='highlight' ? 'highlight' : 'zoom';

		$glob = glob(APPLICATION_HOME."/html/$imageDir/$type/{$this->id}.*");
		if (count($glob)) {
			$filename = basename($glob[0]);
			return BASE_URL."/$imageDir/$type/$filename";
		}
	}

	/**
	 * @param array|string $file  Either an entry from $_FILES or a path to a file
	 */
	public function saveMap($file,$type)
	{
		$imageDir = $this->getMapDirectory();
		$type = $type=='highlight' ? 'highlight' : 'zoom';

		$directory = APPLICATION_HOME."/html/$imageDir/$type";

		Map::saveFile($directory,$file,$this->id);
	}
}

<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
class Internment
{
	private $id;
	private $section;
	private $lot;
	private $book;
	private $pageNumber;
	private $deceasedDate;
	private $lastname;
	private $firstname;
	private $middleInitial;
	private $birthPlace;
	private $lastResidence;
	private $age;
	private $sex;
	private $cemetery_id;
	private $notes;
	private $lot2;

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
				$sql = 'select * from internments where id=?';
				$result = $zend_db->fetchRow($sql,array($id));
			}

			if ($result) {
				foreach ($result as $field=>$value) {
					if ($value) {
						if ($field == 'deceasedDate') {
							$value = (false === strpos($value,'0000')) ? new Date($value) : null;
						}
						$this->$field = $value;
					}
				}
			}
			else {
				throw new Exception('internments/unknownInternment');
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

	}

	/**
	 * Saves this record back to the database
	 */
	public function save()
	{
		$this->validate();

		$data = array();
		$data['section'] = $this->section ? $this->section : null;
		$data['lot'] = $this->lot ? $this->lot : null;
		$data['book'] = $this->book ? $this->book : null;
		$data['pageNumber'] = $this->pageNumber ? $this->pageNumber : null;
		$data['deceasedDate'] = $this->deceasedDate ? $this->deceasedDate->format('Y-m-d') : null;
		$data['lastname'] = $this->lastname ? $this->lastname : null;
		$data['firstname'] = $this->firstname ? $this->firstname : null;
		$data['middleInitial'] = $this->middleInitial ? $this->middleInitial : null;
		$data['birthPlace'] = $this->birthPlace ? $this->birthPlace : null;
		$data['lastResidence'] = $this->lastResidence ? $this->lastResidence : null;
		$data['age'] = $this->age ? $this->age : null;
		$data['sex'] = $this->sex ? $this->sex : null;
		$data['cemetery_id'] = $this->cemetery_id ? $this->cemetery_id : null;
		$data['notes'] = $this->notes ? $this->notes : null;
		$data['lot2'] = $this->lot2 ? $this->lot2 : null;

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
		$zend_db->update('internments',$data,"id='{$this->id}'");
	}

	private function insert($data)
	{
		$zend_db = Database::getConnection();
		$zend_db->insert('internments',$data);
		$this->id = $zend_db->lastInsertId('internments','id');
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
	public function getSection()
	{
		return $this->section;
	}

	/**
	 * @return string
	 */
	public function getLot()
	{
		return $this->lot;
	}

	/**
	 * @return string
	 */
	public function getBook()
	{
		return $this->book;
	}

	/**
	 * @return string
	 */
	public function getPageNumber()
	{
		return $this->pageNumber;
	}

	/**
	 * Returns the date/time in the desired format
	 *
	 * Format is specified using PHP's date() syntax
	 * http://www.php.net/manual/en/function.date.php
	 * If no format is given, the Date object is returned
	 *
	 * @param string $format
	 * @return string|DateTime
	 */
	public function getDeceasedDate($format=null)
	{
		if ($format && $this->deceasedDate) {
			return $this->deceasedDate->format($format);
		}
		else {
			return $this->deceasedDate;
		}
	}

	/**
	 * @return string
	 */
	public function getLastname()
	{
		return $this->lastname;
	}

	/**
	 * @return string
	 */
	public function getFirstname()
	{
		return $this->firstname;
	}

	/**
	 * @return char
	 */
	public function getMiddleInitial()
	{
		return $this->middleInitial;
	}

	/**
	 * @return string
	 */
	public function getBirthPlace()
	{
		return $this->birthPlace;
	}

	/**
	 * @return string
	 */
	public function getLastResidence()
	{
		return $this->lastResidence;
	}

	/**
	 * @return int
	 */
	public function getAge()
	{
		return $this->age;
	}

	/**
	 * @return string
	 */
	public function getSex()
	{
		return $this->sex;
	}

	/**
	 * @return text
	 */
	public function getNotes()
	{
		return $this->notes;
	}

	/**
	 * @return string
	 */
	public function getLot2()
	{
		return $this->lot2;
	}

	/**
	 * @return int
	 */
	public function getCemetery_id()
	{
		return $this->cemetery_id;
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
	 * @param string $string
	 */
	public function setSection($string)
	{
		$this->section = trim($string);
	}

	/**
	 * @param string $string
	 */
	public function setLot($string)
	{
		$this->lot = trim($string);
	}

	/**
	 * @param string $string
	 */
	public function setBook($string)
	{
		$this->book = trim($string);
	}

	/**
	 * @param string $string
	 */
	public function setPageNumber($string)
	{
		$this->pageNumber = trim($string);
	}

	/**
	 * Sets the date
	 *
	 * Date arrays should match arrays produced by getdate()
	 *
	 * Date string formats should be in something strtotime() understands
	 * http://www.php.net/manual/en/function.strtotime.php
	 *
	 * @param int|string|array $date
	 */
	public function setDeceasedDate($date)
	{
		if ($date) {
			$this->deceasedDate = new Date($date);
		}
		else {
			$this->deceasedDate = null;
		}
	}

	/**
	 * @param string $string
	 */
	public function setLastname($string)
	{
		$this->lastname = trim($string);
	}

	/**
	 * @param string $string
	 */
	public function setFirstname($string)
	{
		$this->firstname = trim($string);
	}

	/**
	 * @param char $char
	 */
	public function setMiddleInitial($char)
	{
		$this->middleInitial = $char;
	}

	/**
	 * @param string $string
	 */
	public function setBirthPlace($string)
	{
		$this->birthPlace = trim($string);
	}

	/**
	 * @param string $string
	 */
	public function setLastResidence($string)
	{
		$this->lastResidence = trim($string);
	}

	/**
	 * @param int $int
	 */
	public function setAge($int)
	{
		$this->age = preg_replace("/[^0-9]/","",$int);
	}

	/**
	 * @param string $string
	 */
	public function setSex($string)
	{
		$this->sex = trim($string);
	}

	/**
	 * @param int $int
	 */
	public function setCemetery_id($int)
	{
		$this->cemetery = new Cemetery($int);
		$this->cemetery_id = $int;
	}

	/**
	 * @param Cemetery $cemetery
	 */
	public function setCemetery($cemetery)
	{
		$this->cemetery_id = $cemetery->getId();
		$this->cemetery = $cemetery;
	}

	/**
	 * @param text $text
	 */
	public function setNotes($text)
	{
		$this->notes = $text;
	}

	/**
	 * @param string $string
	 */
	public function setLot2($string)
	{
		$this->lot2 = trim($string);
	}


	//----------------------------------------------------------------
	// Custom Functions
	// We recommend adding all your custom code down here at the bottom
	//----------------------------------------------------------------
}

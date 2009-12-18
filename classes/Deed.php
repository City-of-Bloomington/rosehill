<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
class Deed
{
	private $id;
	private $section;
	private $lot;
	private $lastname1;
	private $firstname1;
	private $middleInitial1;
	private $lastname2;
	private $firstname2;
	private $middleInitial2;
	private $issueDate;
	private $notes;
	private $lot2;
	private $whiteoak;

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
				$sql = 'select * from deeds where id=?';
				$result = $zend_db->fetchRow($sql,array($id));
			}

			if ($result) {
				foreach ($result as $field=>$value) {
					if ($value) {
						if ($field == 'issueDate') {
							$value = (false === strpos($value,'0000')) ? new Date($value) : null;
						}
						$this->$field = $value;
					}
				}
			}
			else {
				throw new Exception('deeds/unknownDeed');
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
		$data['lastname1'] = $this->lastname1 ? $this->lastname1 : null;
		$data['firstname1'] = $this->firstname1 ? $this->firstname1 : null;
		$data['middleInitial1'] = $this->middleInitial1 ? $this->middleInitial1 : null;
		$data['lastname2'] = $this->lastname2 ? $this->lastname2 : null;
		$data['firstname2'] = $this->firstname2 ? $this->firstname2 : null;
		$data['middleInitial2'] = $this->middleInitial2 ? $this->middleInitial2 : null;
		$data['issueDate'] = $this->issueDate ? $this->issueDate->format('Y-m-d') : null;
		$data['notes'] = $this->notes ? $this->notes : null;
		$data['lot2'] = $this->lot2 ? $this->lot2 : null;
		$data['whiteoak'] = $this->whiteoak ? $this->whiteoak : null;

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
		$zend_db->update('deeds',$data,"id='{$this->id}'");
	}

	private function insert($data)
	{
		$zend_db = Database::getConnection();
		$zend_db->insert('deeds',$data);
		$this->id = $zend_db->lastInsertId('deeds','id');
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
	public function getLastname1()
	{
		return $this->lastname1;
	}

	/**
	 * @return string
	 */
	public function getFirstname1()
	{
		return $this->firstname1;
	}

	/**
	 * @return char
	 */
	public function getMiddleInitial1()
	{
		return $this->middleInitial1;
	}

	/**
	 * @return string
	 */
	public function getLastname2()
	{
		return $this->lastname2;
	}

	/**
	 * @return string
	 */
	public function getFirstname2()
	{
		return $this->firstname2;
	}

	/**
	 * @return char
	 */
	public function getMiddleInitial2()
	{
		return $this->middleInitial2;
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
	public function getIssueDate($format=null)
	{
		if ($format && $this->issueDate) {
			return $this->issueDate->format($format);
		}
		else {
			return $this->issueDate;
		}
	}

	/**
	 * @return text
	 */
	public function getNotes()
	{
		return $this->notes;
	}

	/**
	 * @return char
	 */
	public function getLot2()
	{
		return $this->lot2;
	}

	/**
	 * @return char
	 */
	public function getWhiteoak()
	{
		return $this->whiteoak;
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
	public function setLastname1($string)
	{
		$this->lastname1 = trim($string);
	}

	/**
	 * @param string $string
	 */
	public function setFirstname1($string)
	{
		$this->firstname1 = trim($string);
	}

	/**
	 * @param char $char
	 */
	public function setMiddleInitial1($char)
	{
		$this->middleInitial1 = $char;
	}

	/**
	 * @param string $string
	 */
	public function setLastname2($string)
	{
		$this->lastname2 = trim($string);
	}

	/**
	 * @param string $string
	 */
	public function setFirstname2($string)
	{
		$this->firstname2 = trim($string);
	}

	/**
	 * @param char $char
	 */
	public function setMiddleInitial2($char)
	{
		$this->middleInitial2 = $char;
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
	public function setIssueDate($date)
	{
		if ($date) {
			$this->issueDate = new Date($date);
		}
		else {
			$this->issueDate = null;
		}
	}

	/**
	 * @param text $text
	 */
	public function setNotes($text)
	{
		$this->notes = $text;
	}

	/**
	 * @param char $char
	 */
	public function setLot2($char)
	{
		$this->lot2 = $char;
	}

	/**
	 * @param char $char
	 */
	public function setWhiteoak($char)
	{
		$this->whiteoak = $char;
	}


	//----------------------------------------------------------------
	// Custom Functions
	// We recommend adding all your custom code down here at the bottom
	//----------------------------------------------------------------
	/**
	 * Returns the full name of the owner
	 *
	 * @return string
	 */
	public function getOwner($number=1)
	{
		$first = "firstname$number";
		$middle = "middleInitial$number";
		$last = "lastname$number";

		$name = array();
		if ($this->$first) {
			$name[] = $this->$first;
		}
		if ($this->$middle) {
			$name[] = $this->$middle;
		}
		if ($this->$last) {
			$name[] = $this->$last;
		}

		return implode(' ',$name);
	}
}

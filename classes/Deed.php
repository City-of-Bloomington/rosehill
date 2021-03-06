<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
class Deed
{
	private $id;
	private $section_id;
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
	private $cemetery_id;

	private $section;
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
		$data['section_id'] = $this->section_id ? $this->section_id : null;
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
		$data['cemetery_id'] = $this->cemetery_id ? $this->cemetery_id : null;

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

	public function delete()
	{
		if ($this->id) {
			$zend_db = Database::getConnection();
			$zend_db->delete('deeds','id='.$this->id);
		}
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
	public function getSection_id()
	{
		return $this->section_id;
	}

	/**
	 * @return Section
	 */
	public function getSection()
	{
		if ($this->section_id) {
			if (!$this->section) {
				$this->section = new Section($this->section_id);
			}
			return $this->section;
		}
		return null;
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
	 * @param int $int
	 */
	public function setSection_id($int)
	{
		$this->section = new Section($int);
		$this->section_id = $int;
	}

	/**
	 * @param Section $section
	 */
	public function setSection($section)
	{
		$this->section_id = $section->getId();
		$this->section = $section;
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
	 * @param string $string
	 */
	public function setMiddleInitial1($string)
	{
		$this->middleInitial1 = trim($string);
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
	 * @param string $string
	 */
	public function setMiddleInitial2($string)
	{
		$this->middleInitial2 = trim($string);
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

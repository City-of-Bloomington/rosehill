<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
namespace Application\Models;
use Blossom\Classes\ActiveRecord;
use Blossom\Classes\Database;
use Blossom\Classes\ExternalIdentity;

class Deed extends ActiveRecord
{
    protected $tablename = 'deeds';

	protected $section;
	protected $cemetery;

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
     * @param int|string|array $id (ID, email, username)
     */
    public function __construct($id=null)
    {
        if ($id) {
            if (is_array($id)) {
                $this->exchangeArray($id);
            }
            else {
                $zend_db = Database::getConnection();
                $sql = 'select * from deeds where id=?';
                $result = $zend_db->createStatement($sql)->execute([$id]);
                if (count($result)) {
                    $this->exchangeArray($result->current());
                }
                else {
                    throw new \Exception('deeds/unknownDeed');
                }
            }
        }
        else {
            // This is where the code goes to generate a new, empty instance.
            // Set any default values for properties that need it here
        }
    }

	public function validate() { }
	public function save  () { parent::save  (); }
	public function delete() { parent::delete(); }

	//----------------------------------------------------------------
	// Generic Getters and Setters
	//----------------------------------------------------------------
	public function getId()               { return parent::get('id'); }
    public function getLot()              { return parent::get('lot'); }
    public function getLot2()             { return parent::get('lot2'); }
    public function getLastname1()        { return parent::get('lastname1'); }
    public function getLastname2()        { return parent::get('lastname2'); }
    public function getFirstname1()       { return parent::get('firstname1'); }
    public function getFirstname2()       { return parent::get('firstname2'); }
    public function getMiddleInitial1()   { return parent::get('middleInitial1'); }
    public function getMiddleInitial2()   { return parent::get('middleInitial2'); }
    public function getNotes()            { return parent::get('notes'); }
    public function getSection_id()       { return parent::get('section_id'); }
    public function getCemetery_id()      { return parent::get('cemetery_id'); }
    public function getSection()          { return parent::getForeignKeyObject(__namespace__.'\Section', 'section_id'); }
    public function getCemetery()         { return parent::getForeignKeyObject(__namespace__.'\Cemetery', 'cemetery_id'); }
    public function getIssueDate($f=null) { return parent::getDateData('issueDate', $f); }

    public function setLot           ($s) { parent::set('lot',            $s); }
    public function setLot2          ($s) { parent::set('lot2',           $s); }
    public function setLastname1     ($s) { parent::set('lastname1',      $s); }
    public function setLastname2     ($s) { parent::set('lastname2',      $s); }
    public function setFirstname1    ($s) { parent::set('firstname1',     $s); }
    public function setFirstname2    ($s) { parent::set('firstname2',     $s); }
    public function setMiddleInitial1($s) { parent::set('middleInitial1', $s); }
    public function setMiddleInitial2($s) { parent::set('middleInitial2', $s); }
    public function setNotes         ($s) { parent::set('notes',          $s); }
    public function setSection_id ($i) { parent::setForeignKeyField (__namespace__.'\Section',  'section_id',  $i); }
    public function setSection    ($o) { parent::setForeignKeyObject(__namespace__.'\Section',  'section_id',  $o); }
    public function setCemetery_id($i) { parent::setForeignKeyField (__namespace__.'\Cemetery', 'cemetery_id', $i); }
    public function setCemetery   ($o) { parent::setForeignKeyObject(__namespace__.'\Cemetery', 'cemetery_id', $o); }

    /**
     * @param array $post
     */
    public function handleUpdate($post)
    {
        $fields = [
            'section_id', 'cemetery_id', 'lot', 'lot2',
            'firstname1', 'firstname2', 'middleInitial1', 'middleInitial2',
            'lastname1', 'lastname2', 'notes'
        ];
        foreach ($fields as $f) {
            $set = 'set'.ucfirst($f);
            $this->$set($post[$f]);
        }
    }

	//----------------------------------------------------------------
	// Custom Functions
	// We recommend adding all your custom code down here at the bottom
	//----------------------------------------------------------------
	/**
	 * @return string
	 */
	public function getUrl() { return BASE_URL.'/deeds/view?deed_id='.$this->getId(); }
	public function getUri() { return BASE_URI.'/deeds/view?deed_id='.$this->getId(); }

	/**
	 * Returns the full name of the owner
	 *
	 * @param int $number
	 * @return string
	 */
	public function getOwner($number=1)
	{
        $name = [];
        $fields = ['firstname', 'middleInitial', 'lastname'];
        foreach ($fields as $f) {
            $get = 'get'.ucfirst($f).$number;
            if ($this->$get()) { $name[] = $this->$get(); }
        }
        return implode(' ', $name);
	}
}

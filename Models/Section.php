<?php
/**
 * @copyright 2009-2014 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
namespace Application\Models;
use Blossom\Classes\ActiveRecord;
use Blossom\Classes\Database;
use Blossom\Classes\ExternalIdentity;

class Section extends ActiveRecord
{
    protected $tablename = 'sections';

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
                $sql = 'select * from sections where id=?';
                $result = $zend_db->createStatement($sql)->execute([$id]);
                if (count($result)) {
                    $this->exchangeArray($result->current());
                }
                else {
                    throw new \Exception('sections/unknownSection');
                }
            }
        }
        else {
            // This is where the code goes to generate a new, empty instance.
            // Set any default values for properties that need it here
        }
    }

	public function validate()
	{
		if (!$this->getCemetery_id() || !$this->getCode()) {
			throw new Excepction('missingRequiredFields');
		}
	}

	public function save() { parent::save(); }

	//----------------------------------------------------------------
	// Generic Getters and Setters
	//----------------------------------------------------------------
	public function getId()          { return parent::get('id');          }
	public function getCode()        { return parent::get('code');        }
	public function getName()        { return parent::get('name');        }
	public function getCemetery_id() { return parent::get('cemetery_id'); }
    public function getCemetery()    { return parent::getForeignKeyObject(__namespace__.'\Cemetery', 'cemetery_id'); }

    public function setCode($s) { parent::set('code', $s); }
    public function setName($s) { parent::set('name', $s); }
    public function setCemetery_id($i) { parent::setForeignKeyField (__namespace__.'\Cemetery', 'cemetery_id', $i); }
    public function setCemetery   ($o) { parent::setForeignKeyObject(__namespace__.'\Cemetery', 'cemetery_id', $o); }

    public function handleUpdate($post)
    {
        $this->setCode($post['code']);
        $this->setName($post['name']);
        $this->setCemetery_id($post['cemetery_id']);
    }

	//----------------------------------------------------------------
	// Custom Functions
	//----------------------------------------------------------------
	public function __toString()
	{
		return $this->getName() ? $this->getName() : $this->getCode();
	}

	private function getMapDirectory()
	{
		return 'images/cemeteries/'.$this->getCemetery_id();
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

		$glob = glob(APPLICATION_HOME."/public/$imageDir/$type/{$this->getId()}.*");
		if (count($glob)) {
			$filename = basename($glob[0]);
			return BASE_URI."/$imageDir/$type/$filename";
		}
	}

	/**
	 * @param array|string $file  Either an entry from $_FILES or a path to a file
	 */
	public function saveMap($file, $type)
	{
		$imageDir = $this->getMapDirectory();
		$type = $type=='highlight' ? 'highlight' : 'zoom';

		$directory = APPLICATION_HOME."/public/$imageDir/$type";

		Map::saveFile($directory, $file, $this->getId());
	}
}

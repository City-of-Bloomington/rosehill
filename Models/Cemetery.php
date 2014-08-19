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

class Cemetery extends ActiveRecord
{
    protected $tablename = 'cemeteries';

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
                if (ActiveRecord::isId($id)) {
                    $sql = 'select * from cemeteries where id=?';
                }
                else {
                    $sql = 'select * from cemeteries where name=?';
                }
                $result = $zend_db->createStatement($sql)->execute([$id]);
                if (count($result)) {
                    $this->exchangeArray($result->current());
                }
                else {
                    throw new \Exception('cemeteries/unknownCemetery');
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
        if (!$this->getName()) { throw new \Exception('missingName'); }
    }

    public function save() { parent::save(); }

    //----------------------------------------------------------------
    // Generic Getters & Setters
    //----------------------------------------------------------------
    public function getId()           { return parent::get('id'); }
    public function getName()         { return parent::get('name'); }
    public function getGoogleMapUrl() { return parent::get('googleMapUrl'); }

    public function setName        ($s) { parent::set('name',         $s); }
    public function setGoogleMapUrl($s) { parent::set('googleMapUrl', $s); }

    public function handleUpdate($post)
    {
        $this->setName($post['name']);
        $this->setGoogleMapUrl($post['googleMapUrl']);
    }

    //----------------------------------------------------------------
    // Custom functions
    //----------------------------------------------------------------
    public function __toString() { return $this->getName(); }

    /**
     * @return string
     */
    public function getUrl() { return BASE_URL.'/cemeteries/view?cemetery_id='.$this->getId(); }
    public function getUri() { return BASE_URI.'/cemeteries/view?cemetery_id='.$this->getId(); }

    /**
     * @return Zend\Db\Result
     */
    public function getSections()
    {
        if ($this->getId()) {
            $table = new SectionsTable();
            return $table->find(['cemetery_id'=>$this->getId()]);
        }
    }

    /**
     * @return string
     */
    private function getMapDirectory()
    {
        return 'images/cemeteries/'.$this->getId();
    }

    /**
     * Returns the URL to the map image for this cemetery
     *
     * Available map types are:
     *      full, thumb - for the main map
     *
     * @param string $type
     * @return string
     */
    public function getMap($type="full")
    {
        $imageDir = $this->getMapDirectory();
        $filename = $type=='full' ? 'map' : 'map_thumb';

        $glob = glob(APPLICATION_HOME."/public/$imageDir/$filename.*");
        if (count($glob)) {
            $filename = basename($glob[0]);
            return BASE_URL."/$imageDir/$filename";
        }
    }

    /**
     * @param array|string $file  Either an entry from $_FILES or a path to a file
     */
    public function saveMap($file, $type)
    {
        $imageDir = $this->getMapDirectory();
        $name = $type=='full' ? 'map' : 'map_thumb';

        $directory = APPLICATION_HOME."/public/$imageDir";

        Map::saveFile($directory, $file, $name);
    }
}

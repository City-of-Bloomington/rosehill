<?php
/**
 * @copyright 2009-2014 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
namespace Application\Models;

use Blossom\Classes\TableGateway;
use Zend\Db\Sql\Select;
use Zend\Db\Sql\Predicate\Like;
use Zend\Db\Sql\Predicate\PredicateSet;

class DeedsTable extends TableGateway
{
    public function __construct() { parent::__construct('deeds', __namespace__.'\Deed'); }

	private $columns = array(
		'id','section_id','lot','lot2','cemetery_id',
		'lastname1','firstname1','middleInitial1',
		'lastname2','firstname2','middleInitial2',
		'issueDate','notes'
	);

    /**
     * @param array $fields
     * @param string|array $order Multi-column sort should be given as an array
     * @param bool $paginated Whether to return a paginator or a raw resultSet
     * @param int $limit
     */
    public function search($fields=null, $order='id', $paginated=false, $limit=null)
    {
        $select = new Select('deeds');
        if (count($fields)) {
            foreach ($fields as $key=>$value) {
                if (!empty($value)) {
                    switch ($key) {
                        case 'lastname':
                        case 'firstname':
                        case 'middleInitial':
                            if (!empty($value)) {
                                $select->where(
                                    new PredicateSet([
                                        new Like("{$key}1", "$value%"),
                                        new LIke("{$key}2", "$value%")
                                    ], PredicateSet::OP_OR)
                                );
                            }
                        break;

                        default:
                            $select->where([$key=>$value]);
                    }
                }
            }
        }
        return parent::performSelect($select, $order, $paginated, $limit);
    }
}

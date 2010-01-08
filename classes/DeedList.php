<?php
/**
 * A collection class for Deed objects
 *
 * This class creates a zend_db select statement.
 * ZendDbResultIterator handles iterating and paginating those results.
 * As the results are iterated over, ZendDbResultIterator will pass each desired
 * row back to this class's loadResult() which will be responsible for hydrating
 * each Deed object
 *
 * Beyond the basic $fields handled, you will need to write your own handling
 * of whatever extra $fields you need
 */
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
class DeedList extends ZendDbResultIterator
{
	private $columns = array(
		'id','section','lot','lot2','cemetery_id',
		'lastname1','firstname1','middleInitial1',
		'lastname2','firstname2','middleInitial2',
		'issueDate','notes'
	);

	/**
	 * Creates a basic select statement for the collection.
	 *
	 * Populates the collection if you pass in $fields
	 * Setting itemsPerPage turns on pagination mode
	 * In pagination mode, this will only load the results for one page
	 *
	 * @param array $fields
	 * @param int $itemsPerPage Turns on Pagination
	 * @param int $currentPage
	 */
	public function __construct($fields=null,$itemsPerPage=null,$currentPage=null)
	{
		parent::__construct($itemsPerPage,$currentPage);
		if (is_array($fields)) {
			$this->find($fields);
		}
	}

	/**
	 * Populates the collection
	 *
	 * @param array $fields
	 * @param string|array $order Multi-column sort should be given as an array
	 * @param int $limit
	 * @param string|array $groupBy Multi-column group by should be given as an array
	 */
	public function find($fields=null,$order='id',$limit=null,$groupBy=null)
	{
		$this->select->from('deeds');

		// Finding on fields from the deed table is handled here
		if (count($fields)) {
			foreach ($fields as $key=>$value) {
				if ($value) {
					if (in_array($key,$this->columns)) {
						$this->select->where("$key=?",$value);
					}
				}
			}

			if (isset($fields['lastname']) && $fields['lastname']) {
				$this->select->where("(lastname1 like ? or lastname2 like ?)",
									array("$fields[lastname]%"));
			}
			if (isset($fields['firstname']) && $fields['firstname']) {
				$this->select->where("(firstname1 like ? or firstname2 like ?)",
									array("$fields[firstname]%"));
			}
			if (isset($fields['middleInitial']) && $fields['middleInitial']) {
				$this->select->where("(middleInitial1 like ? or middleInitial2 like ?)",
									array("$fields[middleInitial]%"));
			}
		}


		// Finding on fields from other tables requires joining those tables.
		// You can handle fields from other tables by adding the joins here
		// If you add more joins you probably want to make sure that the
		// above foreach only handles fields from the deed table.

		$this->select->order($order);
		if ($limit) {
			$this->select->limit($limit);
		}
		if ($groupBy) {
			$this->select->group($groupBy);
		}
		$this->populateList();
	}

	/**
	 * Hydrates all the Deed objects from a database result set
	 *
	 * This is a callback function, called from ZendDbResultIterator.  It is
	 * called once per row of the result.
	 *
	 * @param int $key The index of the result row to load
	 * @return Deed
	 */
	protected function loadResult($key)
	{
		return new Deed($this->result[$key]);
	}
}

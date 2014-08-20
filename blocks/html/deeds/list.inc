<?php
/**
 * @copyright 2009-2014 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param DeedList $this->deeds
 */
use Application\Models\Person;
use Blossom\Classes\View;
?>
<div>
	<h2><?php
            echo $this->translate(['deed', 'deeds', 2]);
            if (Person::isAllowed('deeds', 'edit')) {
                $helper = $this->template->getHelper('buttonLink');
                echo $helper->buttonLink(
                    BASE_URI.'/deeds/update',
                    $this->translate('add'),
                    'add'
                );
            }
		?>
	</h2>
	<table>
		<thead>
			<tr><th></th>
				<th><?php echo $this->translate(['section', 'sections', 1]); ?>,
                    <?php echo $this->translate('lot'); ?>
                </th>
				<th><?php echo $this->translate('owner'); ?> 1</th>
				<th><?php echo $this->translate('owner'); ?> 2</th>
				<th><?php echo $this->translate('issue_date'); ?></th>
				<th><?php echo $this->translate(['cemetery', 'cemeteries', 1]); ?></th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		<?php
			foreach ($this->deeds as $deed) {
				$editButton   = '';
				$deleteButton = '';
				if (Person::isAllowed('deeds', 'edit')) {
                    $editButton = $helper->buttonLink(
                        BASE_URI.'/deeds/update?deed_id='.$deed->getId(),
                        $this->translate('edit'),
                        'edit'
                    );
                    $return_url = "https://$_SERVER[SERVER_NAME]$_SERVER[REQUEST_URI]";
                    $deleteButton = $helper->buttonLink(
                        BASE_URI."/deeds/delete?deed_id={$deed->getId()};return_url=$return_url",
                        $this->translate('delete'),
                        'delete'
                    );
				}

				$section  = View::escape($deed->getSection());
				$lot      = View::escape($deed->getLot());
				$owner1   = View::escape($deed->getOwner(1));
				$owner2   = View::escape($deed->getOwner(2));
				$date     = $deed->getIssueDate('n/j/Y');
				$cemetery = $deed->getCemetery() ? View::escape($deed->getCemetery()->getName()) : '';
				echo "
				<tr><td>$editButton</td>
					<td>$section, $lot</td>
					<td>$owner1</td>
					<td>$owner2</td>
					<td>$date</td>
					<td>$cemetery</td>
					<td>$deleteButton</td>
				</tr>
				";
			}
		?>
		</tbody>
	</table>
</div>

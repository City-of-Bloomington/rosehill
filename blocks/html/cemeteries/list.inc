<?php
/**
 * @copyright 2009-2014 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param Zend\Db\Result $this->cemeteries
 */
use Application\Models\Person;
use Blossom\Classes\View;
?>
<div>
	<h2><?php
            echo $this->translate(['cemetery', 'cemeteries', 2]);
            if (Person::isAllowed('cemeteries', 'edit')) {
                $helper = $this->template->getHelper('buttonLink');
                echo $helper->buttonLink(
                    BASE_URI.'/cemeteries/update',
                    $this->translate('add'),
                    'add'
                );
            }
		?>
	</h2>
	<ul><?php
			foreach ($this->cemeteries as $cemetery) {
				$editButton = '';
				if (Person::isAllowed('cemeteries', 'edit')) {
                    $editButton = $helper->buttonLink(
                        BASE_URI.'/cemeteries/update?cemetery_id='.$cemetery->getId(),
                        $this->translate('edit'),
                        'edit'
                    );
				}
				$name = View::escape($cemetery->getName());
				echo "
				<li>$editButton
					<a href=\"{$cemetery->getUri()}\">$name</a>
				</li>
				";
			}
		?>
	</ul>
</div>
<?php include APPLICATION_HOME.'/blocks/html/serviceButtons.inc'; ?>

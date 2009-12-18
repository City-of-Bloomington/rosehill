<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @param Interment $this->interment
 */
$mapURL = BASE_URL.'/interments/map.php?interment_id='.$this->interment->getId();
$cemetery = $this->interment->getCemetery();
$name = View::escape($cemetery->getName());
?>
<div id="burialLinks">
	<table>
		<tr><td><a href="<?php echo $mapURL; ?>">
					<img src="<?php echo $cemetery->getMap('thumb'); ?>"
						alt="<?php echo $name; ?>" />
				</a>
			</td>
			<td><a href="<?php echo $mapURL; ?>">
					View Maps of Cemetery and Section
				</a>
			</td>
		</tr>
		<tr><td><a href="<?php echo $cemetery->getGoogleMapURL(); ?>">
					<img src="<?php echo BASE_URL; ?>/images/car.png"
						alt="Direction map to <?php echo $name ?>" />
				</a>
			</td>
			<td><a href="<?php echo $cemetery->getGoogleMapURL(); ?>">
					Get driving directions to <?php echo $name ?>
				</a>
			</td>
		</tr>
	</table>
</div>

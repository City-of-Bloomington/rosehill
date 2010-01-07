<?php
/**
 * Renders buttons for each of the known output formats, pointing to the current URL
 *
 * @copyright 2010 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
$serviceURL = new URL($_SERVER['SERVER_NAME'].$_SERVER['REQUEST_URI']);
$serviceURL->purgeEmptyParameters();
$serviceURL->format = 'xml';
echo "
<button type=\"button\" class=\"xml\" onclick=\"window.open('$serviceURL');\">
	XML
</button>
";
$serviceURL->format = 'json';
echo "
<button type=\"button\" class=\"json\" onclick=\"window.open('$serviceURL');\">
	JSON
</button>
";

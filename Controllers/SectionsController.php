<?php
/**
 * @copyright 2014 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
namespace Application\Controllers;
use Application\Models\Cemetery;
use Application\Models\Section;
use Blossom\Classes\Controller;
use Blossom\Classes\Block;

class SectionsController extends Controller
{
    public function index() { }

    public function update()
    {
        if (!empty($_REQUEST['section_id'])) {
            try {
                $section = new Section($_REQUEST['section_id']);
            }
            catch (\Exception $e) {
                $_SESSION['errorMessages'][] = $e;
            }
        }
        else {
            if (!empty($_REQUEST['cemetery_id'])) {
                try {
                    $section = new Section();
                    $section->setCemetery_id($_REQUEST['cemetery_id']);
                }
                catch (\Exception $e) {
                    $_SESSION['errorMessages'][] = $e;
                }
            }
            else {
                $_SESSION['errorMessages'][] = new \Exception('cemeteries/unknownCemetery');
            }
        }

        if (!isset($section)) {
            header('Location: '.BASE_URL.'/cemeteries');
            exit();
        }

        if (isset($_POST['code'])) {
            try {
                $section->handleUpdate($_POST);
                $section->save();

                if (isset($_FILES)) {
                    if (isset($_FILES['highlight_map']) && $_FILES['highlight_map']['tmp_name']) {
                        $section->saveMap($_FILES['highlight_map'], 'highlight');
                    }
                    if (isset($_FILES['zoom_map']) && $_FILES['zoom_map']['tmp_name']) {
                        $section->saveMap($_FILES['zoom_map'], 'zoom');
                    }
                }

                header('Location: '.BASE_URL.'/cemeteries/view?cemetery_id='.$section->getCemetery_id());
                exit();
            }
            catch (\Exception $e) {
                $_SESSION['errorMessages'][] = $e;
            }
        }

        $this->template->blocks[] = new Block('sections/updateForm.inc', ['section'=>$section]);
    }
}

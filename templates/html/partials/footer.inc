<div id="footer">
	<p>
        <?php
            global $startTime;

            # Calculate the process time
            $endTime = microtime(1);
            $processTime = $endTime - $startTime;
            echo "<!-- Process Time: $processTime -->";
        ?>
	</p>
</div>
<script type="text/javascript" src="<?php echo YUI; ?>/yui/yui-min.js"></script>
<script type="text/javascript" src="<?php echo BASE_URI; ?>/js/functions.js"></script>

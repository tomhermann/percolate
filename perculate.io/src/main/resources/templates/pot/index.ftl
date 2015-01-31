 <div id="content">
    <table class="datatable">
	    <tbody>
		    <tr>
		        <th>Weight</th>
		    </tr>
		    <#list readings as reading>
		    <tr>
		        <td>${reading.weight}</td>
		    </tr>
		    </#list>
	  	</tbody>
  </table>
</div>

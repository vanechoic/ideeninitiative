package awe.ideeninitiative.api;


import awe.ideeninitiative.api.model.Benutzer;

import org.springframework.http.HttpStatus;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Validated
@Api(value = "BenutzernameApi")
public interface BenutzernameApi {

      /**
      * 
      */
      @ApiOperation(value = "", nickname = "benutzerdatenAusgeben", notes = "", tags={ "benutzername" })
      @ApiResponses(value = {
       @ApiResponse(code = 200, message = "erfolgreich"),  @ApiResponse(code = 400, message = "Invalid username supplied"),  @ApiResponse(code = 404, message = "User not found")
      })
      @RequestMapping(value = "/benutzer/{benutzername}",
      method = RequestMethod.GET)
      public ResponseEntity<Benutzer> benutzerdatenAusgeben(@Size(min=1) @ApiParam(value = "The name that needs to be fetched. Use user1 for testing. ",required=true) @PathVariable("benutzername") String benutzername) throws Exception; 

}

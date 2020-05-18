package awe.ideeninitiative.api;


import awe.ideeninitiative.api.model.ApiFehler;
import awe.ideeninitiative.api.model.Benutzer;
import awe.ideeninitiative.api.model.InlineObject;

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
@Api(value = "BenutzerApi")
public interface BenutzerApi {

      /**
      * 
      */
      @ApiOperation(value = "", nickname = "benutzerAbmelden", notes = "", tags={ "benutzer" })
      @ApiResponses(value = {
       @ApiResponse(code = 200, message = "erfolgreich")
      })
      @RequestMapping(value = "/benutzer/logout",
      method = RequestMethod.GET)
      public ResponseEntity<Void> benutzerAbmelden() throws Exception; 


      /**
      * This can only be done by the logged in user.
      */
      @ApiOperation(value = "This can only be done by the logged in user.", nickname = "benutzerAnlegen", notes = "", tags={ "benutzer" })
      @ApiResponses(value = {
       @ApiResponse(code = 200, message = "erfolgreich"),  @ApiResponse(code = 400, message = "Fehlende Eingabe")
      })
      @RequestMapping(value = "/benutzer",
      method = RequestMethod.POST)
      public ResponseEntity<String> benutzerAnlegen(@ApiParam(value = "Created user object" ,required=true )  @Valid @RequestBody Benutzer benutzer) throws Exception; 


      /**
      * 
      */
      @ApiOperation(value = "", nickname = "benutzerAnmelden", notes = "", tags={ "benutzer" })
      @ApiResponses(value = {
       @ApiResponse(code = 200, message = "erfolgreich"),  @ApiResponse(code = 400, message = "Ungültige Benutzerdaten")
      })
      @RequestMapping(value = "/benutzer/login",
      method = RequestMethod.POST)
      public ResponseEntity<String> benutzerAnmelden(@ApiParam(value = "" ,required=true )  @Valid @RequestBody InlineObject anmeldedaten) throws Exception; 


      /**
      * This can only be done by the logged in user.
      */
      @ApiOperation(value = "This can only be done by the logged in user.", nickname = "benutzerdatenAktualisieren", notes = "", tags={ "benutzer" })
      @ApiResponses(value = {
       @ApiResponse(code = 400, message = "Invalid user supplied"),  @ApiResponse(code = 404, message = "User not found")
      })
      @RequestMapping(value = "/benutzer/{benutzername}",
      method = RequestMethod.PUT)
      public ResponseEntity<Void> benutzerdatenAktualisieren(@Size(min=1) @ApiParam(value = "name that need to be updated",required=true) @PathVariable("benutzername") String benutzername,@ApiParam(value = "Updated user object" ,required=true )  @Valid @RequestBody Benutzer body) throws Exception; 


      /**
      * This can only be done by the logged in user.
      */
      @ApiOperation(value = "This can only be done by the logged in user.", nickname = "benutzerdatenLoeschen", notes = "", tags={ "benutzer" })
      @ApiResponses(value = {
       @ApiResponse(code = 400, message = "Invalid username supplied"),  @ApiResponse(code = 404, message = "User not found")
      })
      @RequestMapping(value = "/benutzer/{benutzername}",
      method = RequestMethod.DELETE)
      public ResponseEntity<Void> benutzerdatenLoeschen(@Size(min=1) @ApiParam(value = "The name that needs to be deleted",required=true) @PathVariable("benutzername") String benutzername) throws Exception; 

}

package com.diconiumwvv.storesservice.stores;

import com.diconiumwvv.storesservice.stores.dtos.AddressDTO;
import com.diconiumwvv.storesservice.stores.dtos.StoreDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://dds-wvv-frontend.herokuapp.com"
})
@RestController
@Api(tags = "Stores API")
public class StoresController {

    @ApiOperation(value = "search for stores in your neighborhood")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 500, message = "An unexpected error occurred")
    })
    @GetMapping(value = "/stores/")
    public List<StoreDTO> getStoresByQuery(@RequestParam(required = false) String neighborhood) {
        return Collections.singletonList(getMockStore());
    }

    @ApiOperation(value = "get a specific store by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 500, message = "An unexpected error occurred")
    })
    @GetMapping(value = "/stores/{id}")
    public StoreDTO getStoreById(@PathVariable(required = false) String id) {
        return getMockStore();
    }

    private StoreDTO getMockStore() {
        return StoreDTO.builder()
                .id("d290f1ee-6c54-4b01-90e6-d701748f0851")
                .description(Collections.singletonMap(Locale.GERMANY, "Bei golden! findest du alles, was dich glücklich macht.\n\n„Symbiose“ bringt es ganz gut auf den Punkt, wenn es um die Beschreibung des Ladens vom Mutter-Tochter-Duo Margret und Gisa Schleef geht. Alte und neue Produkte sind gleichwertig vereint. Nostalgie und Moderne können sich auf 40qm Fläche entfalten. Dazwischen sprießen frische Blumen und Zimmerpflanzen. Die Inhaberinnen beweisen Geschmack bei der Gestaltung als auch bei der Produktauswahl in ihrem Laden.\n\ngolden! heißt das gemeinsame Projekt der beiden und ist in der Sonnenallee in Neukölln zu finden. Komm vorbei, lass dich inspirieren, gönn dir eine Kleinigkeit und mach es dir anschließend Zuhause gemütlich. Denn hier wird auf Qualität und Nachhaltigkeit geachtet. Da die alten gesammelten Schätzchen Einzelstücke sind, wechselt das Angebot stetig. Auch das Blumen- und Pflanzenangebot ist je nach Saison wechselnd. In golden! steckt Liebe und Herzblut zweier kreativer Unternehmer-Frauen.\n"))
                .neighborhood("Berlin West")
                .address(AddressDTO.builder()
                        .streetName("Sonnenallee")
                        .streetNumber("64")
                        .postalCode("12045")
                        .city("Berlin")
                        .country("DE")
                        .build())
                .name(Collections.singletonMap(Locale.GERMANY, "Golden Neukölln"))
                .build();
    }
}

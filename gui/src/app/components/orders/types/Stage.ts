export function stageMapper(stage: string) {
    switch (stage) {
        case "MILLING": {
            return "Frezowanie"
        }
        case "POLISHING": {
            return "Polerowanie"
        }
        case "BASE": {
            return "Podkładowanie"
        }
        case "GRINDING": {
            return "Szlifiernia"
        }
        case "PAINTING": {
            return "Lakierowanie"
        }
        case "PACKING": {
            return "Spakowane"
        }
        case "SENT": {
            return "Wysłane"
        }
        case "IN_DELIVERY": {
            return "W trasie"
        }
        case "DELIVERD": {
            return "Dostarczono"
        }
        default: {
            return "---"
        }
    }
}

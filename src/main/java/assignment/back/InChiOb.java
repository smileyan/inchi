package assignment.back;

/**
 * Created by hua on 21/06/16.
 */
public class InChiOb {

    public String getChembl_id() {
        return chembl_id;
    }

    public String getCanonical_smiles() {
        return canonical_smiles;
    }

    public String getStandard_inchi() {
        return standard_inchi;
    }

    public String getStandard_inchi_key() {
        return standard_inchi_key;
    }

    private String chembl_id;
    private String canonical_smiles;
    private String standard_inchi;
    private String standard_inchi_key;

    public boolean isValid() {
        return isValid;
    }

    private boolean isValid = false;

    public InChiOb(String line) {
        String[] items;

        try {
            items = line.split("\t");

            this.chembl_id = items[0];
            // this.canonical_smiles = items[1];
            // this.standard_inchi = items[2];
            this.standard_inchi_key = items[3];
            this.isValid = true;
        } catch (Exception e) {
            this.isValid = false;
        }
    }
}

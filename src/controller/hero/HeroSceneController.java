package controller.hero;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import main.Main;
import model.Hero;
import model.Value;
import model.IO.Reader;
import model.calculator.Calculator;
import model.calculator.Term;
import model.world.Culture;
import model.world.CultureWithElement;
import model.world.Domain;
import model.world.Element;
import model.world.Gift;
import model.world.Handicap;
import model.world.Spell;
import view.hero.TermScene;
import view.spells.SpellBookScene;

public class HeroSceneController {

	@FXML
	private ChoiceBox<Culture> CultureList;

	@FXML
	private RadioButton cultureRadio;

	@FXML
	private ChoiceBox<Element> ElementList;

	@FXML
	private RadioButton elementRadio;

	@FXML
	private ChoiceBox<Integer> layerChoiceBox;

	@FXML
	private Label gPCount;

	@FXML
	private VBox baseValueList;

	@FXML
	private VBox NameColumn;

	@FXML
	private VBox MinColumn;

	@FXML
	private VBox MaxColumn;

	@FXML
	private VBox ValueColumn;

	@FXML
	private VBox BonusColumn;

	@FXML
	private VBox SumColumn;

	@FXML
	private Button startButton;

	@FXML
	private Button tButton;

	@FXML
	private TextArea testOutput;

	@FXML
	private ListView<Gift> selectedGifts;

	@FXML
	private Label giftCostLabel;

	@FXML
	private ListView<Handicap> selectedHandicap;

	@FXML
	private Label handicapCostLabel;

	@FXML
	private ListView<Domain> selectedDomain;

	@FXML
	private Label domainCostLabel;

	@FXML
	private TextArea infoArea;

	@FXML
	private ListView<Gift> allGifts;

	@FXML
	private ListView<Handicap> allHandicaps;

	@FXML
	private ListView<Domain> allDomains;

	@FXML
	private ListView<Spell> spellListElement;

	@FXML
	private ListView<Spell> spellListAllg;

	private Hero hero;
	private int gp = 500;

	private int domainCost = 0;

	private int giftCost = 0;

	private int handicapCost = 0;

	@FXML
	void initialize() {
		loadStuff();
		
		infoArea.setWrapText(true);
		
		hero = new Hero();
		gPCount.setText("" + gp);

		spellListAllg.getItems().addAll(Element.getElementFromName("Allg").getSpells());

		layerChoiceBox.getSelectionModel().selectedItemProperty().addListener(y -> updateGPCount());

		for (Value v : Hero.baseValueList) {
			Label nameLabel = new Label(v.getName());
			nameLabel.setFont(new Font(16));
			nameLabel.setTextAlignment(TextAlignment.CENTER);
			NameColumn.getChildren().add(nameLabel);

			Label minLabel = new Label("0");
			minLabel.setFont(new Font(16));
			minLabel.setTextAlignment(TextAlignment.CENTER);
			MinColumn.getChildren().add(minLabel);

			TextField valueField = new TextField("10");
			valueField.textProperty().addListener(y ->	valueChange());;
			ValueColumn.getChildren().add(valueField);

			Label maxLabel = new Label("0");
			maxLabel.setFont(new Font(16));
			maxLabel.setTextAlignment(TextAlignment.CENTER);
			MaxColumn.getChildren().add(maxLabel);

			Label bonusLabel = new Label("0");
			bonusLabel.setFont(new Font(16));
			bonusLabel.setTextAlignment(TextAlignment.CENTER);
			BonusColumn.getChildren().add(bonusLabel);
		}

		for (int i = 2; i < NameColumn.getChildren().size(); i++) {
			int sum = Integer.parseInt(((TextField) ValueColumn.getChildren().get(i)).getText());
			Label sumLabel = new Label("" + sum);
			sumLabel.setFont(new Font(16));
			sumLabel.setTextAlignment(TextAlignment.CENTER);
			SumColumn.getChildren().add(sumLabel);
		}

		ToggleGroup tg = new ToggleGroup();
		cultureRadio.setToggleGroup(tg);
		cultureRadio.setSelected(true);
		elementRadio.setToggleGroup(tg);

		CultureList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Culture>() {
			@Override
			public void changed(ObservableValue<? extends Culture> observable, Culture oldValue, Culture newValue) {
				if (cultureRadio.isSelected()) {
					ElementList.getItems().clear();
					Culture c = CultureList.getSelectionModel().getSelectedItem();
					ElementList.getItems().addAll(c.getElements());
				}
				valueChange();
			}
		});

		ElementList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Element>() {
			@Override
			public void changed(ObservableValue<? extends Element> observable, Element oldValue, Element newValue) {
				Element e = ElementList.getSelectionModel().getSelectedItem();
				if (elementRadio.isSelected()) {
					CultureList.getItems().clear();
					for (Culture c : Culture.allCultures) {
						if (c.getElements().contains(e)) {
							CultureList.getItems().add(c);
						}
					}
				}
				spellListElement.getItems().clear();
				if (e != null) {
					spellListElement.getItems().addAll(e.getSpells());
				}
				valueChange();
			}
		});

		
		

		
		// set infoArea Lambda expression
		
		spellListElement.getSelectionModel().selectedItemProperty().addListener ( (arg, oldVal, newVal) ->  infoArea.setText(newVal.getText()));
		
		spellListAllg.getSelectionModel().selectedItemProperty().addListener ( (arg, oldVal, newVal) ->  infoArea.setText(newVal.getText()));
		
		allGifts.getSelectionModel().selectedItemProperty().addListener ( (arg, oldVal, newVal) -> infoArea.setText(newVal.getText()));

		selectedGifts.getSelectionModel().selectedItemProperty().addListener ( (arg, oldVal, newVal) -> infoArea.setText(newVal==null?"":newVal.getText()));

		allHandicaps.getSelectionModel().selectedItemProperty().addListener ( (arg, oldVal, newVal) -> infoArea.setText(newVal.getText()));

		selectedHandicap.getSelectionModel().selectedItemProperty().addListener ( (arg, oldVal, newVal) -> infoArea.setText(newVal==null?"":newVal.getText()));

		allDomains.getSelectionModel().selectedItemProperty().addListener ( (arg, oldVal, newVal) -> infoArea.setText(newVal.getText()));

		selectedDomain.getSelectionModel().selectedItemProperty().addListener ( (arg, oldVal, newVal) -> infoArea.setText(newVal==null?"":newVal.getText()));

		updateGPCount();
	}

	private void loadStuff() {

		for (int i = 1; i < 8; i++) {
			layerChoiceBox.getItems().add(i);
		}
		layerChoiceBox.getSelectionModel().select(0);

		CultureList.getItems().addAll(Culture.allCultures);

		ElementList.getItems().addAll(Element.allElements);

		allGifts.getItems().addAll(Gift.allGifts);

		allHandicaps.getItems().addAll(Handicap.allHandicaps);

		allDomains.getItems().addAll(Domain.allDomains);

	}

	protected void valueChange() {
		if (CultureList.getSelectionModel().getSelectedItem() != null
				&& ElementList.getSelectionModel().getSelectedItem() != null) {
			hero.setCwE(CultureWithElement.getCultureWithElement(CultureList.getSelectionModel().getSelectedItem(), ElementList.getSelectionModel().getSelectedItem()));
		}
		if(hero.getCwE()!=null) {
			
			for(int i=0;i< Hero.baseValueList.size();i++) {
				int numb = Integer.parseInt(((TextField) (ValueColumn.getChildren().get(i+2))).getText());
				hero.addValue(new Value(Hero.baseValueList.get(i).getName(),numb)); 
			}	
			
			updateMinMaxBonusValues();
			updateGPCount();
			updateSumValue();
		}
		

	}

	private void updateSumValue() {

		while (SumColumn.getChildren().size() > 2) { // clearLists
			SumColumn.getChildren().remove(2);
		}

		for (int i = 2; i < NameColumn.getChildren().size(); i++) {
			Value valuebyName = hero.getValuebyName(Hero.baseValueList.get(i-2).getName());
			Label label = new Label("" + valuebyName.getNumber());
			label.setFont(new Font(16));
			label.setTextAlignment(TextAlignment.CENTER);
			SumColumn.setAlignment(Pos.CENTER);
			SumColumn.getChildren().add(label);
		}

	}

	private void updateGPCount() {
		int sum = 0;
		for (int i = 2; i < ValueColumn.getChildren().size(); i++) {
			String s = ((TextField) (ValueColumn.getChildren().get(i))).getText();
			sum += Integer.parseInt(s);
		}
		sum += (layerChoiceBox.getSelectionModel().getSelectedItem() - 1) * 10;
		gp = Hero.START_GP - sum;

		// Gift,Handicap,Domain

		gp = gp - giftCost - domainCost + handicapCost;

		// setText
		gPCount.setText("" + gp);
		if (gp < 0) {
			gPCount.setTextFill(Color.RED);
		} else {
			gPCount.setTextFill(Color.BLACK);
		}
	}

	@FXML
	void cultureRadioChoose(ActionEvent event) {
		CultureList.getItems().addAll(Culture.allCultures);
		Element e = ElementList.getSelectionModel().getSelectedItem();
		ElementList.getItems().clear();
		ElementList.getItems().addAll(CultureList.getSelectionModel().getSelectedItem().getElements());
		ElementList.getSelectionModel().select(e);
	}

	@FXML
	void elementRadioChoose(ActionEvent event) {
		ElementList.getItems().addAll(Element.allElements);
		Culture cs = CultureList.getSelectionModel().getSelectedItem();
		CultureList.getItems().clear();
		for (Culture c : Culture.allCultures) {
			if (c.getElements().contains(ElementList.getSelectionModel().getSelectedItem())) {
				CultureList.getItems().add(c);
			}
		}
		CultureList.getSelectionModel().select(cs);
	}

	private void updateMinMaxBonusValues() {
		if (CultureList.getSelectionModel().getSelectedItem() != null
				&& ElementList.getSelectionModel().getSelectedItem() != null) {

			Culture c = CultureList.getSelectionModel().getSelectedItem();
			Element e = ElementList.getSelectionModel().getSelectedItem();

			CultureWithElement cWe = CultureWithElement.getCultureWithElement(c, e);

			hero.setCwE(cWe);
			
			updateSumValue();
			
			while (MinColumn.getChildren().size() > 2) { // clearLists
				MinColumn.getChildren().remove(2);
				MaxColumn.getChildren().remove(2);
				BonusColumn.getChildren().remove(2);
			}
			
			for (int i = 2; i < NameColumn.getChildren().size(); i++) { // setValues
																		// from
																		// cWe

				int value = Integer.parseInt(((TextField) (ValueColumn.getChildren().get(i))).getText());

				int min = cWe.getMinValues().get(i - 2).getNumber();
				Label minLabel = new Label(min + "");
				minLabel.setFont(new Font(16));
				minLabel.setTextAlignment(TextAlignment.RIGHT);
				MinColumn.setAlignment(Pos.CENTER);
				MinColumn.getChildren().add(minLabel);

				if (value < min) {
					minLabel.setTextFill(Color.RED);
				}

				int max = cWe.getMaxValues().get(i - 2).getNumber();
				Label maxLabel = new Label(max + "");
				maxLabel.setFont(new Font(16));
				maxLabel.setTextAlignment(TextAlignment.RIGHT);
				MaxColumn.setAlignment(Pos.CENTER);
				MaxColumn.getChildren().add(maxLabel);

				if (value > max) {
					maxLabel.setTextFill(Color.RED);
				}

				Label bonusLabel = new Label(cWe.getBonusValues().get(i - 2).getNumber() + "");
				bonusLabel.setFont(new Font(16));
				bonusLabel.setTextAlignment(TextAlignment.CENTER);
				BonusColumn.setAlignment(Pos.CENTER);
				BonusColumn.getChildren().add(bonusLabel);

			}
		}

	}

	@FXML
	void selectDomain(MouseEvent event) {
		if (event.getClickCount() == 2) {
			Domain d = allDomains.getSelectionModel().getSelectedItem();
			selectedDomain.getItems().add(d);
			allDomains.getItems().remove(d);
		}
		domainCost = 0;
		for (Domain d : selectedDomain.getItems()) {
			domainCost += d.getCost();
		}
		domainCostLabel.setText("" + domainCost);
		updateGPCount();
	}

	@FXML
	void selectGift(MouseEvent event) {
		if (event.getClickCount() == 2) {
			Gift g = allGifts.getSelectionModel().getSelectedItem();
			selectedGifts.getItems().add(g);
			allGifts.getItems().remove(g);
		}
		giftCost = 0;
		for (Gift g : selectedGifts.getItems()) {
			giftCost += g.getCost();
		}
		giftCostLabel.setText("" + giftCost);
		updateGPCount();
	}

	@FXML
	void selectHandicap(MouseEvent event) {
		if (event.getClickCount() == 2) {
			Handicap h = allHandicaps.getSelectionModel().getSelectedItem();
			selectedHandicap.getItems().add(h);
			allHandicaps.getItems().remove(h);
		}
		handicapCost = 0;
		for (Handicap h : selectedHandicap.getItems()) {
			handicapCost += h.getCost();
		}
		handicapCostLabel.setText("" + handicapCost);
		updateGPCount();
	}

	@FXML
	void deselectDomain(MouseEvent event) {
		if (event.getClickCount() == 2) {
			Domain d = selectedDomain.getSelectionModel().getSelectedItem();
			allDomains.getItems().add(d);
			selectedDomain.getItems().remove(d);
		}
		domainCost = 0;
		for (Domain d : selectedDomain.getItems()) {
			domainCost += d.getCost();
		}
		domainCostLabel.setText("" + domainCost);
		updateGPCount();
	}

	@FXML
	void deselectGift(MouseEvent event) {
		if (event.getClickCount() == 2) {
			Gift g = selectedGifts.getSelectionModel().getSelectedItem();
			allGifts.getItems().add(g);
			selectedGifts.getItems().remove(g);
		}
		giftCost = 0;
		for (Gift g : selectedGifts.getItems()) {
			giftCost += g.getCost();
		}
		giftCostLabel.setText("" + giftCost);
		updateGPCount();
	}

	@FXML
	void deselectHandicap(MouseEvent event) {
		if (event.getClickCount() == 2) {
			Handicap h = selectedHandicap.getSelectionModel().getSelectedItem();
			allHandicaps.getItems().add(h);
			selectedHandicap.getItems().remove(h);
		}
		handicapCost = 0;
		for (Handicap h : selectedHandicap.getItems()) {
			handicapCost += h.getCost();
		}
		handicapCostLabel.setText("" + handicapCost);
		updateGPCount();
	}

	@FXML
	void startTest(ActionEvent event) {
		testOutput.setText("teset1");
		for (Value v : hero.getValues()) {
			testOutput.setText(testOutput.getText() + "\n" + v.getName() + " : " + v.getNumber() + "=> "
					+ Calculator.calc(hero, v.getTerm()));
		}
	}

	@FXML
	void goToTermScene(ActionEvent event) {
		//Main.primStage.setScene(SpellBookScene.spellBookScene);
	}

	@FXML
	void startValues(ActionEvent event) {

	}

	@FXML
	void backButtonClicked(ActionEvent event) {
		Main.primStage.setScene(Main.mainMenueScene);
	}
}

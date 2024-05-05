import { render, screen, waitFor } from "@testing-library/react";
import Home from "../app/page";
import "@testing-library/jest-dom";
jest.mock("../components/map/MapComponent");
import user from "@testing-library/user-event";
import userEvent from "@testing-library/user-event";
import SideCreationView from "@/components/side-menu/side-form-view/side-creation-view";
import SideInfoCommon from "@/components/side-menu/side-info-view/side-info-common";
import SideFormViewCommon from "@/components/side-menu/side-form-view/side-form-view-common";

describe("Home", () => {
  beforeEach(() => {
    user.setup();
    render(<Home />);
  });

  test("user account nav bar is rendered", () => {
    const navigationElement = screen.getByRole("listitem");
    expect(navigationElement).toBeInTheDocument();
  });

  test("map is rendered", () => {
    const mockMapComponent = screen.getByTestId("mockMapComponent");
    expect(mockMapComponent).toBeInTheDocument();
  });
});

describe("Functions", () => {

  beforeEach(() => {
    user.setup();
    render(<Home />);
  });
  test("handlePolygonComplete", async () => {
    const completePolygonBtn = screen.getByTestId("completePolygon");
    await userEvent.click(completePolygonBtn);

    expect(screen.getByText("Create New Boundary")).toBeInTheDocument();
  });

  test("toggleViewToInfo", async () => {
    render(<SideCreationView />);

    const closeBtn = screen.getByTestId("closeButton");
    await userEvent.click(closeBtn);

    await waitFor(() => {
      const alertsContent = screen.getAllByText(/event/i);
      expect(alertsContent.length).toBeGreaterThan(0);
    });
  });

  test("handleSave", async () => {
    const onCloseMock = jest.fn();
    render(<SideCreationView />);

    await userEvent.click(screen.getByRole("button", { name: /save/i }));

    expect(onCloseMock).toHaveBeenCalledTimes(1);
  });

  describe("handleClose", () => {
    test("calls onClearPolygon and then onClose when isCreationView true", async () => {
      const onCloseMock = jest.fn();
      const onClearPolygonMock = jest.fn();

      render(
        <SideFormViewCommon
          title="Create New Boundary"
          boundaryPlaceholder="Enter Boundary Name"
          descriptionPlaceholder="Enter Description"
        />
      );

      await userEvent.click(screen.getByTestId("closeButton"));

      expect(onClearPolygonMock).toHaveBeenCalledTimes(1);
      expect(onCloseMock).toHaveBeenCalledTimes(1);

      const onClearPolygonCallOrder =
        onClearPolygonMock.mock.invocationCallOrder[0];
      const onCloseCallOrder = onCloseMock.mock.invocationCallOrder[0];
      expect(onClearPolygonCallOrder).toBeLessThan(onCloseCallOrder);
    });

    test("only calls onClose when isCreationView false", async () => {
      const onCloseMock = jest.fn();

      render(
        <SideFormViewCommon
          title="Create New Boundary"
          boundaryPlaceholder="Enter Boundary Name"
          descriptionPlaceholder="Enter Description"
        />
      );

      await userEvent.click(screen.getByTestId("closeButton"));

      expect(onCloseMock).toHaveBeenCalledTimes(1);
    });
  });
});

describe("Side Info View", () => {
  beforeEach(() => {
    user.setup();
    render(<Home />);
  });

  describe("Side Info View Elements Rendered", () => {
    test("search bar", () => {
      const searchElement = screen.getByRole("searchbox");
      expect(searchElement).toBeInTheDocument();
    });

    test("alerts tab", () => {
      const alertsTabElement = screen.getByRole("tab", { name: "Alerts" });
      expect(alertsTabElement).toBeInTheDocument();
    });

    test("boundary tab", () => {
      const boundaryTabElement = screen.getByRole("tab", {
        name: "Boundaries",
      });
      expect(boundaryTabElement).toBeInTheDocument();
    });

    test("search button", () => {
      const searchButtonElement = screen.getByTestId("search-button");
      expect(searchButtonElement).toBeInTheDocument();
    });

    test("subscriptions checkbox", () => {
      const checkboxElement = screen.getByRole("checkbox");
      expect(checkboxElement).toBeInTheDocument();
    });
  });

  describe("Correct elements are rendered on tab interaction", () => {
    test("alerts content", async () => {
      const alertsContent = screen.getAllByText(/event/i);
      expect(alertsContent.length).toBeGreaterThan(0);
    });

    test("boundaries content", async () => {
      const boundariesTab = screen.getByRole("tab", { name: "Boundaries" });
      await user.click(boundariesTab);

      const boundariesContent = screen.getAllByText(/description/i);
      expect(boundariesContent.length).toBeGreaterThan(0);
    });
  });

  test("side-info-common alert data renders correctly", async () => {
    const mockData = [
      {
        title: "Test Alert1",
        sub: true,
        header: ["Time1"],
        body: ["Snow"],
      },
      {
        title: "Test Alert 2",
        sub: false,
        header: ["Time2"],
        body: ["Snow"],
      },
    ];

    render(
      <SideInfoCommon
        data={mockData}
        subCheckValue={false}
        setSubCheckValue={() => { }}
      />
    );

    const alert1 = screen.getAllByText("Test Alert1");
    expect(alert1.length).toBeGreaterThan(0);

    const time1 = screen.getAllByText("Time1");
    expect(time1.length).toBeGreaterThan(0);

    const snowOccurrences = screen.getAllByText("Snow");
    expect(snowOccurrences.length).toBeGreaterThanOrEqual(0);

    const alert2 = screen.getAllByText("Test Alert 2");
    expect(alert2.length).toBeGreaterThan(0);

    const time2 = screen.getAllByText("Time2");
    expect(time2.length).toBeGreaterThan(0);
  });

  test("side-info-common boundary data renders correctly", async () => {
    const mockData = [
      {
        name: "Test Boundary 1",
        description: "Description",
        subscribed: true,
      },
      {
        name: "Test Boundary 2",
        description: "Description",
        subscribed: false,
      },
    ];

    render(
      <SideInfoCommon data={mockData} />
    );

    const bound1 = screen.getAllByText("Test Boundary 1");
    expect(bound1.length).toBeGreaterThan(0);

    const des1 = screen.getAllByText("Description");
    expect(des1.length).toBeGreaterThan(0);

    const bound2 = screen.getAllByText("Test Boundary 2");
    expect(bound2.length).toBeGreaterThan(0);

    const des2 = screen.getAllByText("Description");
    expect(des1.length).toBeGreaterThan(0);

    const badgeElements = screen.getAllByText("Subscribed");
    expect(badgeElements.length).toBe(1);

  });
});

jest.mock('@/services/BoundaryService', () => ({
  postBoundary: jest.fn().mockResolvedValue({ success: true }),
}));

describe("Side Creation View", () => {
  beforeEach(() => {
    user.setup();
    render(<Home />);
  });

  describe("Side creation view elements are rendered", () => {
    test("boundary title", () => {
      render(<SideCreationView />);
      const boundaryInput = screen.getByPlaceholderText("Enter Boundary Name");
      expect(boundaryInput).toBeInTheDocument();
    });

    test("boundary description", () => {
      render(<SideCreationView />);
      const descriptionInput = screen.getByPlaceholderText("Enter Description");
      expect(descriptionInput).toBeInTheDocument();
    });

    test("subscribe checkbox", () => {
      render(<SideCreationView />);
      const checkbox = screen.getByLabelText("Subscribe");
      expect(checkbox).toBeInTheDocument();
    });

    test("save button", () => {
      render(<SideCreationView />);
      const saveButton = screen.getByRole("button", { name: /save/i });
      expect(saveButton).toBeInTheDocument();
    });

    test("close button", () => {
      render(<SideCreationView />);
      const closeButton = screen.getByTestId("closeButton");
      expect(closeButton).toBeInTheDocument();
    });
  });
});

import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import { Input } from "@/components/ui/input";
import { ScrollArea } from "@/components/ui/scroll-area";
import { Separator } from "@/components/ui/separator";
import { MagnifyingGlassIcon } from "@radix-ui/react-icons";

export function SideInfoCommon({ data, subCheckValue, setSubCheckValue }) {
  // Function to handle checkbox changes
  const handleCheckboxChange = () => {
    console.log("value", subCheckValue);
    setSubCheckValue((prevValue) => !prevValue);
  };

  return (
    <div id="side-info-common" className="h-full">
      <div className="flex flex-col max-w-full gap-3 h-full">
        <div className="flex w-full items-center space-x-2">
          <Input type="search" placeholder="Search" />
          <Button type="submit">
            <MagnifyingGlassIcon />
          </Button>
        </div>
        <div id="sub_checkbox" className="flex items-center space-x-2">
          <Checkbox
            id="filterSubs"
            checked={!subCheckValue}
            onCheckedChange={handleCheckboxChange}
          />
          <label
            htmlFor="filterSubs"
            className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
          >
            Subscriptions
          </label>
        </div>
        <ScrollArea className="h-5/6 w-full rounded-md border">
          {data.map((item, index: number) => (
            <DataSelect MockData={item} TitleIndex={index}></DataSelect>
          ))}
        </ScrollArea>
      </div>
    </div>
  );
}

/**
 *
 * @returns html elements for either alert or boundary info view
 */
function DataSelect({ MockData: item, TitleIndex: index }) {
  if (item.sub === undefined) {
    return (
      <div key={index} className="p-2">
        <h4 className="text-base">{item.title}</h4>
        {/* Loop through the header list */}
        {item.header.map((headerItem: string, headerIndex: number) => (
          <h3 key={headerIndex} className="text-sm">
            {headerItem}
          </h3>
        ))}
        {/* Loop through the body list */}
        {item.body.map((bodyItem: string, bodyIndex: number) => (
          <h4 key={bodyIndex} className="text-sm text-muted-foreground pb-2">
            {bodyItem}
          </h4>
        ))}
        <Separator />
      </div>
    );
  } else {
    return (
      <div key={index} className="p-2">
        <h4 className="text-base">{item.title}</h4>
        <div id="sub_checkbox" className="flex items-center space-x-2 py-1">
          <Checkbox id="boundarySubs" defaultChecked={item.sub} />
          <label
            htmlFor="boundarySubs"
            className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
          >
            Subscribe
          </label>
        </div>
        {/* Loop through the header list */}
        {item.header.map((headerItem: string, headerIndex: number) => (
          <h3 key={headerIndex} className="text-sm">
            {headerItem}
          </h3>
        ))}
        {/* Loop through the body list */}
        {item.body.map((bodyItem: string, bodyIndex: number) => (
          <h4 key={bodyIndex} className="text-sm text-muted-foreground pb-2">
            {bodyItem}
          </h4>
        ))}
        <Separator />
      </div>
    );
  }
}
